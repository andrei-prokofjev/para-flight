package com.apro.paraflight.ui.mapbox

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.ui.BaseViewModel
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.camera.CameraUpdate
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapboxViewModel @Inject constructor(
  private val mapboxInteractor: MapboxInteractor
) : BaseViewModel() {

  private val _style = MutableLiveData<String>()
  val style: LiveData<String> = _style

  private val _liveLocationData = MutableLiveData<Location>()
  val locationData: LiveData<Location> = _liveLocationData

  private val _routeData = MutableLiveData<List<Point>>()
  val routeData: LiveData<List<Point>> = _routeData

  // <position, duration>
  private val _cameraPosition = MutableLiveData<Pair<CameraUpdate, Int>>()
  val cameraPosition: LiveData<Pair<CameraUpdate, Int>> = _cameraPosition


  init {
    viewModelScope.launch {
      mapboxInteractor.mapStyleFlow.collect { _style.postValue(it) }
    }

    viewModelScope.launch {
      mapboxInteractor.cameraPositionFlow.collect { _cameraPosition.postValue(it to 1000) }
    }
  }
}