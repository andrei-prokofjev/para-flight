package com.apro.core.preferenes.api


interface MapboxPreferences {

  var mapStyle: MapStyle

  enum class MapStyle {
    MAPBOX_STREETS,
    LIGHT,
    SATELLITE
  }
}