package com.apro.core.preferenes.api

import kotlinx.coroutines.flow.Flow

interface MapboxPreferences {

  var mapStyle: MapStyle

  enum class MapStyle {
    MAPBOX_STREETS,
    LIGHT,
    SATELLITE
  }

  fun styleFlow(): Flow<MapStyle>
}