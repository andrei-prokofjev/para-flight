package com.apro.paraflight.ui.flight

import android.location.Location
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.util.Speed
import com.apro.core.util.event.EventBus
import com.apro.core.util.metersPerSecond
import com.apro.paraflight.mapbox.FlightRepository
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
  private val flightRepository: FlightRepository,
  private val settingsPreferences: SettingsPreferences
) : FlightInteractor {

  private var scope: CoroutineScope? = null

  private val updateLocationChannel = ConflatedBroadcastChannel<FlightPoint>()

  private val duration = 0L

  private val distance = 0

  enum class FlightState {
    PREPARING,
    FLIGHT,
    LANDED
  }

  private var flightState = FlightState.PREPARING

  private val flightRoute = mutableListOf<FlightPoint>()

  override fun init() {
    clear()

    flightRepository.requestLocationUpdates()

    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })

    var baseAltitude = 0f
    val altitudes = mutableListOf<Double>()

    scope?.launch {
      flightRepository.updateLocationFlow().collect {
        val flightPoint = createFlightPoint(it)

        when (flightState) {
          FlightState.PREPARING -> {
            if (altitudes.isEmpty()) {
              for (a: Int in 0..9) {
                altitudes.add(0, it.altitude)
              }
            }
            altitudes.removeAt(0)
            altitudes.add(it.altitude)
            var summ = 0
            altitudes.forEach { summ += it.roundToInt() }
            baseAltitude = summ / altitudes.size.toFloat()
            if (it.speed > settingsPreferences.takeOffSpeed && (it.altitude - baseAltitude).absoluteValue > settingsPreferences.takeOffAltDiff) {
              // take off
              val last = flightRoute.subList(flightRoute.size - 10, flightRoute.size - 1)
              flightRoute.clear()
              flightRoute.addAll(last)
              flightState = FlightState.FLIGHT
            }
          }
          FlightState.FLIGHT -> {
            flightRoute.add(flightPoint)
            if (it.speed < 3) {
              flightState = FlightState.LANDED
            }
          }

          FlightState.LANDED -> {

          }
        }



        println(">>> base $baseAltitude " + (it.altitude - baseAltitude).absoluteValue)


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

  private fun createFlightPoint(location: Location) = FlightPoint(
    location.latitude, location.longitude, location.altitude.toInt(),
    location.speed.metersPerSecond.convertTo(Speed.KilometerPerHour).amount.toInt(),
    distance, duration)

}