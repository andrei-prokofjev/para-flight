package com.apro.core.location.engine.api

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationEngine {
  fun requestLocationUpdates()
  fun updateLocationFlow(): Flow<Location>
  fun removeLocationUpdates()

  fun requestLastLocation()
  fun lastLocationFlow(): Flow<Location>

  fun clear()
}