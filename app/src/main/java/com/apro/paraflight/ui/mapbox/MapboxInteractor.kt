package com.apro.paraflight.ui.mapbox

import android.location.Location
import com.apro.core.location.engine.api.LocationEngine
import kotlinx.coroutines.flow.Flow

interface MapboxInteractor {
  fun requestLocationUpdates(locationEngine: LocationEngine)
  fun locationUpdatesFlow(): Flow<Location>
  fun removeLocationUpdate()

  fun requestLastLocation(locationEngine: LocationEngine)
  fun lastLocationFlow(): Flow<Location>

  fun changeMapStyle()

  fun uiSettingsFlow(): Flow<MapboxSettings>
  var uiSettings: MapboxSettings
}