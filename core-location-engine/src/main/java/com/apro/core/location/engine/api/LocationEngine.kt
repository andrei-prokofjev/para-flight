package com.apro.core.location.engine.api

import android.location.Location
import com.apro.core.model.FlightModel
import kotlinx.coroutines.flow.Flow

interface LocationEngine {
  fun requestLocationUpdates()
  fun removeLocationUpdates()

  fun getLastLocation()
  fun lastLocationFlow(): Flow<Location>

  fun updateLocationFlow(): Flow<Location>


  fun clear()
  fun updateRoute(flightData: List<FlightModel>)

  companion object {
    const val DEFAULT_INTERVAL_IN_MILLISECONDS = 500L
    const val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5
  }

  fun routeFlow(): Flow<List<FlightModel>>
}