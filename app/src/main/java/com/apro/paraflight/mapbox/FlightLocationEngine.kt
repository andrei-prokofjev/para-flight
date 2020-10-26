package com.apro.paraflight.mapbox

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface FlightLocationEngine {
  fun updateLocationFlow(): Flow<Location>
  fun requestLocationUpdates()
  fun removeLocationUpdates()
  fun getLastLocation(callback: (Location) -> Unit)
  fun clear()
}