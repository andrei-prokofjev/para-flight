package com.apro.paraflight.ui.mapbox

data class MapboxSettings(
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
  val increaseRotateThresholdWhenScaling: Boolean = true,
  val increaseScaleThresholdWhenRotating: Boolean = true,
)
