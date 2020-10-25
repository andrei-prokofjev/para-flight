package com.apro.paraflight.events

import com.apro.core.util.event.Event
import com.mapbox.mapboxsdk.camera.CameraUpdate

class MyLocationEvent(val cameraUpdate: CameraUpdate, val duration: Int) : Event
