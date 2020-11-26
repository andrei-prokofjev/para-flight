package com.apro.core.location.engine.api

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationEngine {
  fun requestLocationUpdates()
  fun updateLocationFlow(): Flow<Location>
  fun removeLocationUpdates()

  suspend fun lastLocation(): Location

  fun clear()
}