package com.apro.paraflight.ui.mapbox

import androidx.lifecycle.ViewModel
import com.mapbox.mapboxsdk.maps.MapboxMap

class MapboxViewModel(mapboxMap: MapboxMap) : ViewModel() {

  init {
    println(">>> mapboxMap $mapboxMap")
  }


}