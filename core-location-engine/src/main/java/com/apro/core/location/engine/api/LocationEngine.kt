package com.apro.core.location.engine.api

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationEngine {
  fun requestLocationUpdates()
  fun removeLocationUpdates()

  fun getLastLocation()

  fun lastLocationFlow(): Flow<Location>

  fun updateLocationFlow(): Flow<Location>

  fun clear()
}