@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.apro.paraflight.ui.flight

import android.graphics.PointF
import android.location.Location
import android.text.format.DateUtils
import com.apro.core.model.FlightModel
import com.apro.core.model.weather.CoordModel
import com.apro.core.navigation.AppRouter
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.util.Speed
import com.apro.core.util.metersPerSecond
import com.apro.core.util.roundTo
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.core.FitCircle
import com.apro.paraflight.core.WindVector
import com.apro.paraflight.ui.mapbox.MapboxInteractor
import com.apro.paraflight.ui.mapbox.MapboxSettings
import com.apro.paraflight.util.ResourceProvider
import com.apro.paraflight.voice.VoiceGuidanceInteractor
import com.mapbox.geojson.Point
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfMeasurement
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.*


class FlightInteractorImpl @Inject constructor(
  val appRouter: AppRouter,
  val resources: ResourceProvider,
  private val mapboxInteractor: MapboxInteractor,
  private val settingsPreferences: SettingsPreferences,
  private val voiceGuidanceInteractor: VoiceGuidanceInteractor,
  mapboxSettings: MapboxSettings
) : FlightInteractor {

  private var scope: CoroutineScope? = null

  private val flightStateChannel = ConflatedBroadcastChannel<FlightInteractor.FlightState>()

  private val debugChannel = ConflatedBroadcastChannel<String>()
  override val debugFlow = debugChannel.asFlow()

  private val flightDataChannel = ConflatedBroadcastChannel<FlightModel>()
  override fun flightDataFlow() = flightDataChannel.asFlow()

  private fun flightStateFlow() = flightStateChannel.asFlow()

  private var flightState: FlightInteractor.FlightState = FlightInteractor.FlightState.PREPARING
    set(value) {
      field = value
      scope?.launch { flightStateChannel.send(value) }
    }

  private val flightData = mutableListOf<FlightModel>()

  private val baseAltitudes = mutableListOf<Double>() // calculate average base altitudes

  private var timeNotificationTicker: ReceiveChannel<Unit>? = null

  init {

    mapboxInteractor.mapboxSettings = mapboxSettings

    clear()
    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })


    scope?.launch(Dispatchers.IO) {


    }

    var takeOffTime = 0L
    var duration = 0L
    var totalDistance = 0.0
    var baseAltitude = 0f

    var once = true

    flightState = FlightInteractor.FlightState.PREPARING

    scope?.launch {
      mapboxInteractor.locationUpdatesFlow().collect {


        if (once) {
          val l = mapboxInteractor.getEngine()?.lastLocation()
          println(">>> l: $l")
          l?.let {


            if (mapboxInteractor.getEngine() != null) {
              val w = DI.appComponent.weatherApi().getWeatherByLocation(CoordModel(l.altitude, l.longitude))
              println(">>> w " + w)
              mapboxInteractor.getEngine()?.setSeaLevelPressure(w.main.pressure)
              once = false
            }
          }
        }

        val flightModel = FlightModel(
          lng = it.longitude,
          lat = it.latitude,
          alt = if (it.altitude == 0.0) 0.0 else it.altitude + settingsPreferences.altitudeOffset - 20,
          groundSpeed = it.speed,
          bearing = it.bearing
        )


        when (flightState) {

          FlightInteractor.FlightState.PREPARING -> {
            flightDataChannel.send(flightModel)

            baseAltitude = getBaseAltitude(it)
            if (it.speed.metersPerSecond.convertTo(Speed.KilometerPerHour).amount > settingsPreferences.takeOffSpeed) {
              flightState = FlightInteractor.FlightState.TAKE_OFF
            }
          }

          FlightInteractor.FlightState.TAKE_OFF -> {
            totalDistance += getDistance(it)
            val fd = flightModel.copy(dist = totalDistance)
            flightDataChannel.send(fd)
            flightData.add(fd)

            if ((baseAltitude - it.altitude).absoluteValue > settingsPreferences.takeOffAltDiff) {
              takeOffTime = it.time
              flightState = FlightInteractor.FlightState.FLIGHT
            }
          }

          FlightInteractor.FlightState.FLIGHT -> {
            totalDistance += getDistance(it)
            duration = it.time - takeOffTime

            val windVector = if (settingsPreferences.windDetector && flightData.size > 3) {
              val a = flightData.takeLast(settingsPreferences.windDetectionPoints * SettingsPreferences.WIND_DIRECTION_POINTS_STEP)
              val array = a.map {
                val angle = Math.toRadians(it.bearing.toDouble()).toFloat()
                println(">>> azim: $ " + it.bearing)
                PointF(sin(angle) * it.groundSpeed, cos(angle) * it.groundSpeed)
              }.toTypedArray()
              FitCircle.taubinNewton(array)
            } else WindVector()

            val windSpeed = windVector.let { sqrt((it.x.pow(2) + it.y.pow(2)).toDouble()).toFloat() }
            var winDirection = windVector.let { Math.toDegrees(asin(it.x.toDouble() / windSpeed)) }.toFloat()

            //  println(">>> speed: $windSpeed")

            if (windVector.y < 0) winDirection = 180 - windSpeed
            if (windVector.y > 0 && windVector.x < 0) winDirection += 360

            if (windSpeed == 0f) {
              winDirection = 0f
            }


            val fd = flightModel.copy(
              dist = totalDistance,
              duration = duration,
              windSpeed = windSpeed,
              airSpeed = windVector.radius,
              winDirection = winDirection
            )
            flightDataChannel.send(fd)
            flightData.add(fd)

            if (it.speed.metersPerSecond.convertTo(Speed.KilometerPerHour).amount <= settingsPreferences.minFlightSpeed) {
              //  flightState = FlightInteractor.FlightState.LANDED
            }
          }

          FlightInteractor.FlightState.LANDED -> {

          }
        }
      }
    }

    scope?.launch {
      flightStateFlow().collect {
        //  testChannel.send(it.toString())

        when (it) {
          FlightInteractor.FlightState.PREPARING -> {
            totalDistance = 0.0
            duration = 0L
            flightData.clear()
          }

          FlightInteractor.FlightState.TAKE_OFF -> {

          }

          FlightInteractor.FlightState.FLIGHT -> {
            duration = 0
            totalDistance = 0.0


            voiceGuidanceInteractor.speak(resources.getString(R.string.tts_you_are_off_the_ground_and_on_your_own_at_this_point))

            // voiceGuidanceInteractor.speak(resources.getString(R.string.tts_climbing_through_x_meters_at_x_kilometers_per_hour))
            //  timeNotificationTicker = ticker(settingsPreferences.timeNotificationInterval, settingsPreferences.timeNotificationInterval)
          }

          FlightInteractor.FlightState.LANDED -> {
            // todo: save flightData into db
            voiceGuidanceInteractor.speak(resources.getString(R.string.tts_you_have_successfully_reached_the_ground))
//            voiceGuidanceInteractor.speak(resources.getString(R.string.tts_we_certainly_enjoyed_serving_you_in_flight_today))
//            voiceGuidanceInteractor.speak(resources.getString(R.string.tts_hope_to_serve_you_soon_again))

            withContext(Dispatchers.Main) {
              showFlightSummary(totalDistance, duration)
            }

            baseAltitudes.clear()
            timeNotificationTicker?.cancel()

            //flightState = FlightInteractor.FlightState.PREPARING
          }
        }
      }
    }

    scope?.launch {
      timeNotificationTicker?.consumeAsFlow()?.collect {
        val minutes = duration / DateUtils.MINUTE_IN_MILLIS % 60
        val hours = duration / DateUtils.HOUR_IN_MILLIS

        val tts = if (hours > 0) resources.getString(R.string.tts_flight_time_x_hours_x_minutes, hours, minutes)
        else resources.getString(R.string.tts_flight_time_x_minutes, minutes)

        voiceGuidanceInteractor.speak(tts)
      }
    }
  }

  // todo: redesign
  private fun showFlightSummary(totalDistance: Double, duration: Long) {
    var sum = 0f
    flightData.forEach { sum += it.groundSpeed }
    val averageSpeed = sum / flightData.size

    appRouter.openModalDialogFragment(FlightSummaryBottomSheetDialogFragment.create(
      totalDistance.toInt(),
      duration,
      averageSpeed.roundTo(1)))
  }


  private fun getDistance(it: Location): Double {
    if (flightData.isEmpty()) return 0.0
    return TurfMeasurement.distance(
      Point.fromLngLat(it.longitude, it.latitude), // current point
      Point.fromLngLat(flightData.last().lng, flightData.last().lat), // previous point
      TurfConstants.UNIT_METERS
    )
  }

  private fun getBaseAltitude(it: Location): Float {
    if (baseAltitudes.isEmpty()) {
      for (a: Int in 0..9) {
        baseAltitudes.add(it.altitude)
      }
    } else {
      baseAltitudes.removeFirst()
      baseAltitudes.add(it.altitude)
    }

    var sum = 0
    baseAltitudes.forEach { sum += it.roundToInt() }
    return sum / baseAltitudes.size.toFloat()
  }

  override fun clear() {
    flightData.clear()
    timeNotificationTicker?.cancel()
    scope?.coroutineContext?.cancelChildren()
    scope?.launch { cancel() }
  }

}