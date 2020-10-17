package com.apro.paraflight.preferences.api

import com.mapbox.mapboxsdk.maps.Style

interface MapboxPreferences {

  var mapStyle: MapStyle

  enum class MapStyle(val style: String) {
    MAPBOX_STREETS(Style.MAPBOX_STREETS),
    SATELLITE(Style.SATELLITE),
    LIGHT(Style.LIGHT)
  }
}