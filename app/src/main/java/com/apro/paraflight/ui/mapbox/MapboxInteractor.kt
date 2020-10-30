package com.apro.paraflight.ui.mapbox

import com.mapbox.mapboxsdk.camera.CameraUpdate
import kotlinx.coroutines.flow.Flow

interface MapboxInteractor {
  val cameraPositionFlow: Flow<CameraUpdate>

  val mapStyleFlow: Flow<String>

  fun changeMapStyle()

  fun navigateToCurrentPosition()

  fun clear()
}