package com.apro.paraflight.ui.mapbox


data class MapboxSettings(
  val cameraMode: CameraMode = CameraMode.TRACK_GPS,
  val renderMode: RenderMode = RenderMode.GPS,
  val compassEnabled: Boolean = false,
  val zoom: Double = 12.0,
  val locationComponentEnabled: Boolean = false,
  val disableRotateWhenScaling: Boolean = true,
  val rotateGesturesEnabled: Boolean = true,
  val tiltGesturesEnabled: Boolean = true,
  val zoomGesturesEnabled: Boolean = false,
  val doubleTapGesturesEnabled: Boolean = false,
  val quickZoomGesturesEnabled: Boolean = false,
  val scrollGesturesEnabled: Boolean = true,
  val horizontalScrollGesturesEnabled: Boolean = true,
  val scaleVelocityAnimationEnabled: Boolean = true,
  val rotateVelocityAnimationEnabled: Boolean = true,
  val flingVelocityAnimationEnabled: Boolean = true,
  val increaseScaleThresholdWhenRotating: Boolean = false,
  val windDetector: Boolean = false
) {

  companion object {
    val DefaultMapboxSettings = MapboxSettings()

    val FlightMapboxSettings = MapboxSettings().copy(
      compassEnabled = true,
      zoom = 16.0,
      locationComponentEnabled = true,
      rotateGesturesEnabled = false,
      doubleTapGesturesEnabled = false,
      horizontalScrollGesturesEnabled = true,
      scrollGesturesEnabled = true,
      zoomGesturesEnabled = true
    )

    val ReplayFlightMapboxSettings = MapboxSettings().copy(
      cameraMode = CameraMode.TRACKING,
      renderMode = RenderMode.NORMAL,
      compassEnabled = true,
      horizontalScrollGesturesEnabled = false,
      scrollGesturesEnabled = false
    )
  }
}

