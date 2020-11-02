package com.apro.paraflight.mapbox

import android.location.Location
import com.apro.paraflight.ui.flight.FlightModel
import kotlinx.coroutines.flow.Flow

interface MapboxLocationEngineRepository {
  fun updateLocationFlow(): Flow<Location>

  fun lastLocationFlow(): Flow<Location>

  fun requestLocationUpdates()

  fun removeLocationUpdates()

  fun clear()

  fun getLastLocation()
  fun updateRoute(map: List<FlightModel>)

  fun routeFlow(): Flow<List<FlightModel>>
}