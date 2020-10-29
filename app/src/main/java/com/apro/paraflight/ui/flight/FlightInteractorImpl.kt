package com.apro.paraflight.ui.flight

import android.location.Location
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.util.event.EventBus
import com.apro.paraflight.R
import com.apro.paraflight.interactors.VoiceGuidanceInteractor
import com.apro.paraflight.mapbox.MapboxLocationEngineRepository
import com.apro.paraflight.util.ResourceProvider
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
  private val mapboxLocationEngineRepository: MapboxLocationEngineRepository,
  private val settingsPreferences: SettingsPreferences,
  private val voiceGuidanceInteractor: VoiceGuidanceInteractor
) : FlightInteractor {

  private var scope: CoroutineScope? = null

  private val updateLocationChannel = ConflatedBroadcastChannel<FlightModel>()
  private val flightStateChannel = ConflatedBroadcastChannel<FlightInteractor.FlightState>()

  private val timeNotificationChannel = ConflatedBroadcastChannel<Long>()

//  val timeFlow = timeNotificationChannel.asFlow()

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
    mapboxLocationEngineRepository.requestLocationUpdates()

    var takeOffTime = 0L
    var duration = 0L
    var totalDistance = 0.0

    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })

    flightState = FlightInteractor.FlightState.PREPARING

    scope?.launch {
      mapboxLocationEngineRepository.updateLocationFlow().collect {
        val flightModel = FlightModel(lon = it.longitude, lat = it.latitude, alt = it.altitude, speed = it.speed)
        when (flightState) {
          FlightInteractor.FlightState.PREPARING -> {
            totalDistance = 0.0
            duration = 0L
            getBaseAltitude(it)
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
              val baseAltitude = getBaseAltitude(it)
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

//    scope?.launch {
//     timeFlow.collect {
//       println(">>> collect $ " + it)
//     }
//    }

    scope?.launch {
      flightStateFlow().collect {
        when (it) {
          FlightInteractor.FlightState.PREPARING -> {
            // todo: speak

            // todo: move to the FLIGHT state
            notifyTime(timeNotificationChannel, duration, 5000L)


          }
          FlightInteractor.FlightState.TAKE_OFF -> {
            // todo:
          }
          FlightInteractor.FlightState.FLIGHT -> {
            voiceGuidanceInteractor.speak(resources.string(R.string.take_off_detection))
            delay(1000)
            voiceGuidanceInteractor.speak(resources.string(R.string.tts_have_a_nice_flight))
          }
          FlightInteractor.FlightState.LANDED -> {

            voiceGuidanceInteractor.speak(resources.string(R.string.tts_have_a_nice_flight))
            // todo: save flightData into db
            flightData.clear()
            baseAltitudes.clear()
            // todo: show modal bottom sheet
          }
        }
      }
    }
  }

  private fun getDistance(it: Location): Double {
    if (flightData.isEmpty()) return 0.0
    val currentPoint = Point.fromLngLat(it.longitude, it.latitude)
    return TurfMeasurement.distance(currentPoint,
      Point.fromLngLat(flightData.last().lon, flightData.last().lat),
      TurfConstants.UNIT_METERS)
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

  private suspend fun notifyTime(channel: SendChannel<Long>, duration: Long, delay: Long) {
    while (true) {
      delay(delay)
      println(">>> duration: $duration")
      channel.send(duration)
    }
  }

  override fun clear() {
    scope?.launch { cancel() }
    mapboxLocationEngineRepository.removeLocationUpdates()
    mapboxLocationEngineRepository.clear()
  }


}