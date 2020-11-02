package com.apro.core.preferenes.api

import com.mapbox.mapboxsdk.maps.Style
import kotlinx.coroutines.flow.Flow

interface MapboxPreferences {

  var mapStyle: MapStyle

  enum class MapStyle(val style: String) {
    MAPBOX_STREETS(Style.MAPBOX_STREETS),
    LIGHT(Style.LIGHT),
    SATELLITE(Style.SATELLITE)
  }

  fun mapStyleFlow(): Flow<MapStyle>
}