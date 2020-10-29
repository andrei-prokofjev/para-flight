package com.apro.paraflight.ui.flight

import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.util.event.EventBus
import com.apro.paraflight.mapbox.FlightRepository
import com.mapbox.geojson.Point
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class FlightInteractorImpl @Inject constructor(
  val evenBus: EventBus,
  private val flightRepository: FlightRepository,
  private val settingsPreferences: SettingsPreferences
) : FlightInteractor {

  private var scope: CoroutineScope? = null

  private val updateLocationChannel = ConflatedBroadcastChannel<FlightModel>()

  enum class FlightState {
    PREPARING,
    FLIGHT,
    LANDED
  }

  private var flightState = FlightState.PREPARING

  private val flightData = mutableListOf<FlightModel>()

  override fun init() {
    clear()

    val takeOffTime = System.currentTimeMillis()

    var totalDistance = 0.0

    flightRepository.requestLocationUpdates()

    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })

    var baseAltitude = 0f
    val altitudes = mutableListOf<Double>()

    scope?.launch {
      flightRepository.updateLocationFlow().collect {

        val currentPoint = Point.fromLngLat(it.longitude, it.latitude, it.altitude)

//        Point.f()
//        val lastFlightData = if (flightData.isEmpty()) FlightDataModel else flightData.last()
//        val prevPoint = Point.f(lastFlightData)

        // totalDistance += TurfMeasurement.distance(currentPoint, prevPoint, TurfConstants.UNIT_METERS)
        val flightPoint = FlightModel(
          lon = it.longitude,
          lat = it.latitude,
          alt = it.altitude,
          speed = it.speed,
          dist = totalDistance,
          duration = System.currentTimeMillis() - takeOffTime
        )

//        when (flightState) {
//          FlightState.PREPARING -> {
//            if (altitudes.isEmpty()) {
//              for (a: Int in 0..9) {
//                altitudes.add(0, it.altitude)
//              }
//            }
//            altitudes.removeAt(0)
//            altitudes.add(it.altitude)
//            var summ = 0
//            altitudes.forEach { summ += it.roundToInt() }
//            baseAltitude = summ / altitudes.size.toFloat()
//            if (it.speed > settingsPreferences.takeOffSpeed && (it.altitude - baseAltitude).absoluteValue > settingsPreferences.takeOffAltDiff) {
//              // take off
//              val last = flightData.subList(flightData.size - 10, flightData.size - 1)
//              flightData.clear()
//              flightData.addAll(last)
//              flightState = FlightState.FLIGHT
//            }
//          }
//          FlightState.FLIGHT -> {
//            flightData.add(flightPoint)
//            if (it.speed < 3) {
//              flightState = FlightState.LANDED
//            }
//          }
//
//          FlightState.LANDED -> {
//
//          }
//        }


        updateLocationChannel.send(flightPoint)
      }
    }
  }


  override fun updateLocationFlow() = updateLocationChannel.asFlow()

  override fun clear() {
    scope?.launch { cancel() }
    flightRepository.removeLocationUpdates()
    flightRepository.clear()
  }


}