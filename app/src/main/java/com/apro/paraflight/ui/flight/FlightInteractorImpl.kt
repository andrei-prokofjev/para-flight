package com.apro.paraflight.ui.flight

import android.location.Location
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.util.event.EventBus
import com.apro.paraflight.mapbox.MapboxLocationEngineRepository
import com.mapbox.geojson.Point
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfMeasurement
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class FlightInteractorImpl @Inject constructor(
  val evenBus: EventBus,
  private val mapboxLocationEngineRepository: MapboxLocationEngineRepository,
  private val settingsPreferences: SettingsPreferences,
  //private val voiceGuidanceInteractor: VoiceGuidanceInteractor
) : FlightInteractor {

  private var scope: CoroutineScope? = null

  private val updateLocationChannel = ConflatedBroadcastChannel<FlightModel>()
  private val flightStateChannel = ConflatedBroadcastChannel<FlightInteractor.FlightState>()

  override fun updateLocationFlow() = updateLocationChannel.asFlow()
  override fun flightStateFlow() = flightStateChannel.asFlow()


  private var flightState: FlightInteractor.FlightState = FlightInteractor.FlightState.PREPARING
    set(value) {
      field = value
      scope?.launch { flightStateChannel.send(value) }
    }


  private val flightData = mutableListOf<FlightModel>()

  private val altitudes = mutableListOf<Double>()

  override fun init() {
    clear()
    mapboxLocationEngineRepository.requestLocationUpdates()

    var takeOffTime = 0L
    var duration = 0L
    var totalDistance = 0.0

    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })

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
                //   voiceGuidanceInteractor.speak("take off")
                flightState = FlightInteractor.FlightState.FLIGHT
              }
            }
          }
          FlightInteractor.FlightState.FLIGHT -> {
            totalDistance += getDistance(it)
            duration = System.currentTimeMillis() - takeOffTime
            flightData.add(flightModel.copy(dist = totalDistance, duration = duration))
          }
          FlightInteractor.FlightState.LANDED -> {
            // todo: save flightData into db
            // todo: clear flightData
            // todo: show modal bottom sheet
            altitudes.clear()
            flightState = FlightInteractor.FlightState.PREPARING
          }

        }
        updateLocationChannel.send(flightModel.copy(dist = totalDistance, duration = duration))
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
    if (altitudes.isEmpty()) {
      for (a: Int in 0..9) {
        altitudes.add(it.altitude)
      }
    } else {
      altitudes.removeFirst()
      altitudes.add(it.altitude)
    }

    var sum = 0
    altitudes.forEach { sum += it.roundToInt() }
    return sum / altitudes.size.toFloat()
  }


  override fun clear() {
    scope?.launch { cancel() }
    mapboxLocationEngineRepository.removeLocationUpdates()
    mapboxLocationEngineRepository.clear()
  }


}