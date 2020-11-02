package com.apro.paraflight.ui.mapbox

import android.location.Location
import com.apro.core.preferenes.api.MapboxPreferences
import com.mapbox.geojson.Point
import kotlinx.coroutines.flow.Flow

interface MapboxInteractor {
  fun mapStyleFlow(): Flow<MapboxPreferences.MapStyle>

  fun updateLocationFlow(): Flow<Location>

  fun changeMapStyle()

  fun navigateToCurrentPosition()
  fun clear()
  fun routeLocationFlow(): Flow<List<Point>>
}