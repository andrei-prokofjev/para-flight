package com.apro.paraflight.ui.mapbox

enum class CameraMode(val mode: Int) {
  NONE(com.mapbox.mapboxsdk.location.modes.CameraMode.NONE),
  NONE_COMPASS(com.mapbox.mapboxsdk.location.modes.CameraMode.NONE_COMPASS),
  NONE_GPS(com.mapbox.mapboxsdk.location.modes.CameraMode.NONE_GPS),
  TRACKING(com.mapbox.mapboxsdk.location.modes.CameraMode.TRACKING),
  TRACK_COMPASS(com.mapbox.mapboxsdk.location.modes.CameraMode.TRACKING_COMPASS),
  TRACK_GPS(com.mapbox.mapboxsdk.location.modes.CameraMode.TRACKING_GPS),
  TRACK_GPS_NORTH(com.mapbox.mapboxsdk.location.modes.CameraMode.TRACKING_GPS_NORTH)
}