package com.apro.paraflight.ui.mapbox

sealed class MapboxSettings(
  val compassEnabled: Boolean = false,
  val zoom: Double = 12.0,
  val locationComponentEnabled: Boolean = false,
  val disableRotateWhenScaling: Boolean = true,
  val rotateGesturesEnabled: Boolean = true,
  val tiltGesturesEnabled: Boolean = true,
  val zoomGesturesEnabled: Boolean = true,
  val scrollGesturesEnabled: Boolean = true,
  val horizontalScrollGesturesEnabled: Boolean = true,
  val doubleTapGesturesEnabled: Boolean = true,
  val quickZoomGesturesEnabled: Boolean = true,
  val scaleVelocityAnimationEnabled: Boolean = true,
  val rotateVelocityAnimationEnabled: Boolean = true,
  val flingVelocityAnimationEnabled: Boolean = true,
  val increaseScaleThresholdWhenRotating: Boolean = true,
) {
  object DefaultMapboxSettings : MapboxSettings()

  object FlightScreenMapboxSettings : MapboxSettings(
    compassEnabled = true,
    zoom = 16.0,
    locationComponentEnabled = true,
    rotateGesturesEnabled = false,
    doubleTapGesturesEnabled = false,
    horizontalScrollGesturesEnabled = true,
    scrollGesturesEnabled = true,
    zoomGesturesEnabled = true
  )
}

