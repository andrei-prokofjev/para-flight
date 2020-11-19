package com.apro.paraflight.ui.mapbox

enum class RenderMode(val mode: Int) {
  GPS(com.mapbox.mapboxsdk.location.modes.RenderMode.GPS),
  COMPASS(com.mapbox.mapboxsdk.location.modes.RenderMode.COMPASS),
  NORMAL(com.mapbox.mapboxsdk.location.modes.RenderMode.NORMAL),
}