package com.apro.paraflight.mapbox

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface MapboxLocationEngineRepository {
  fun updateLocationFlow(): Flow<Location>

  fun lastLocationFlow(): Flow<Location>

  fun requestLocationUpdates()

  fun removeLocationUpdates()

  fun clear()

  fun getLastLocation()
}