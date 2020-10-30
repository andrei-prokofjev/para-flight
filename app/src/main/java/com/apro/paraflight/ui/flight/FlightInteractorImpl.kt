package com.apro.paraflight.ui.flight

import android.location.Location
import android.text.format.DateUtils
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.util.event.EventBus
import com.apro.paraflight.R
import com.apro.paraflight.mapbox.MapboxLocationEngineRepository
import com.apro.paraflight.util.ResourceProvider
import com.apro.paraflight.voiceguidance.VoiceGuidanceInteractor
import com.mapbox.geojson.Point
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfMeasurement
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class FlightInteractorImpl @Inject constructor(
  val evenBus: EventBus,
  val resources: ResourceProvider,
  private val mapboxRepository: MapboxLocationEngineRepository,
  private val settingsPreferences: SettingsPreferences,
  private val voiceGuidanceInteractor: VoiceGuidanceInteractor
) : FlightInteractor {

  private var scope: CoroutineScope? = null

  private val updateLocationChannel = ConflatedBroadcastChannel<FlightModel>()
  private val flightStateChannel = ConflatedBroadcastChannel<FlightInteractor.FlightState>()

  private val timeNotificationChannel = ConflatedBroadcastChannel<Long>()

  override fun updateLocationFlow() = updateLocationChannel.asFlow()

  override fun flightStateFlow() = flightStateChannel.asFlow()

  private var flightState: FlightInteractor.FlightState = FlightInteractor.FlightState.PREPARING
    set(value) {
      field = value
      scope?.launch { flightStateChannel.send(value) }
    }

  private val flightData = mutableListOf<FlightModel>()

  private val baseAltitudes = mutableListOf<Double>() // calculate average base altitudes

  override fun init() {
    clear()
    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })

    mapboxRepository.requestLocationUpdates()

    var takeOffTime = 0L
    var duration = 0L
    var totalDistance = 0.0
    var baseAltitude = 0f

    flightState = FlightInteractor.FlightState.PREPARING

    scope?.launch {
      mapboxRepository.updateLocationFlow().collect {

        val flightModel = FlightModel(lon = it.longitude, lat = it.latitude, alt = it.altitude, speed = it.speed)

        when (flightState) {

          FlightInteractor.FlightState.PREPARING -> {
            totalDistance = 0.0
            duration = 0L
            baseAltitude = getBaseAltitude(it)
            flightData.clear()
            if (it.speed >= settingsPreferences.takeOffSpeed) {
              takeOffTime = System.currentTimeMillis()
              flightData.add(flightModel.copy(duration = takeOffTime))
              flightState = FlightInteractor.FlightState.TAKE_OFF
            }
          }

          FlightInteractor.FlightState.TAKE_OFF -> {
            totalDistance += getDistance(it)
            duration = System.currentTimeMillis() - takeOffTime
            flightData.add(flightModel.copy(dist = totalDistance, duration = duration))
            if (it.speed < settingsPreferences.takeOffSpeed) {
              flightState = FlightInteractor.FlightState.PREPARING
            } else {
              if ((baseAltitude - it.altitude).absoluteValue > settingsPreferences.takeOffAltDiff) {
                flightState = FlightInteractor.FlightState.FLIGHT
              }
            }
          }

          FlightInteractor.FlightState.FLIGHT -> {
            totalDistance += getDistance(it)
            duration = System.currentTimeMillis() - takeOffTime
            flightData.add(flightModel.copy(dist = totalDistance, duration = duration))
            if (it.speed < 1f) {
              flightState = FlightInteractor.FlightState.LANDED
            }
          }

          FlightInteractor.FlightState.LANDED -> {
            flightState = FlightInteractor.FlightState.PREPARING

          }
        }

        updateLocationChannel.send(flightModel.copy(dist = totalDistance, duration = duration))
      }
    }

    scope?.launch {
      flightStateFlow().collect {
        when (it) {
          FlightInteractor.FlightState.PREPARING -> {

          }
          FlightInteractor.FlightState.TAKE_OFF -> {
            // todo:
          }
          FlightInteractor.FlightState.FLIGHT -> {

            notifyFlightTime(timeNotificationChannel, duration, settingsPreferences.timeNotificationInterval)

            voiceGuidanceInteractor.speak(resources.getString(R.string.tts_you_are_off_the_ground_and_on_your_own_at_this_point))
            delay(500)
            voiceGuidanceInteractor.speak(resources.getString(R.string.tts_climbing_through_x_meters_at_x_kilometers_per_hour))
          }
          FlightInteractor.FlightState.LANDED -> {
            // todo: save flightData into db
            voiceGuidanceInteractor.speak(resources.getString(R.string.tts_you_have_successfully_reached_the_ground))
            delay(500)
            voiceGuidanceInteractor.speak(resources.getString(R.string.tts_we_certainly_enjoyed_serving_you_in_flight_today))
            delay(500)
            voiceGuidanceInteractor.speak(resources.getString(R.string.tts_hope_to_serve_you_soon_again))
            baseAltitudes.clear()
            // todo: show modal bottom sheet
          }
        }
      }
    }
  }

  private fun getDistance(it: Location): Double {
    if (flightData.isEmpty()) return 0.0
    return TurfMeasurement.distance(
      Point.fromLngLat(it.longitude, it.latitude), // current point
      Point.fromLngLat(flightData.last().lon, flightData.last().lat), // previous point
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

  private suspend fun notifyFlightTime(channel: SendChannel<Long>, duration: Long, delay: Long) {
    while (true) {
      if (flightState != FlightInteractor.FlightState.FLIGHT) break
      delay(delay)
      val minutes = duration / DateUtils.MINUTE_IN_MILLIS % 60
      val hours = duration / DateUtils.HOUR_IN_MILLIS

      val tts = if (hours > 0) resources.getString(R.string.tts_flight_time_x_hours_x_minutes, hours, minutes)
      else resources.getString(R.string.tts_flight_time_x_minutes, minutes)

      voiceGuidanceInteractor.speak(tts)
      channel.send(duration)
    }
  }

  override fun clear() {
    scope?.coroutineContext?.cancelChildren()
    scope?.launch { cancel() }
    mapboxRepository.removeLocationUpdates()
    mapboxRepository.clear()
  }
}