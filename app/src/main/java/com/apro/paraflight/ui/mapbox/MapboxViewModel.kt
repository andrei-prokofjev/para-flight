package com.apro.paraflight.ui.mapbox

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.ui.BaseViewModel
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdate
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
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
  private val _myCurrentPosition = MutableLiveData<Pair<CameraUpdate, Int>>()
  val myCurrentPosition: LiveData<Pair<CameraUpdate, Int>> = _myCurrentPosition


  init {
    viewModelScope.launch {
      mapboxInteractor.mapStyleFlow().collect { _style.postValue(it.style) }
    }

    viewModelScope.launch {
      mapboxInteractor.updateLocationFlow().collect {
        val position = CameraPosition.Builder()
          .target(LatLng(it.latitude, it.longitude))
//          .zoom(12.0)
          .build()

        _myCurrentPosition.postValue(CameraUpdateFactory.newCameraPosition(position) to 500)

//        if (flightInteractor.flightState == FlightInteractor.FlightState.FLIGHT) {
//          _routeData.postValue(flightInteractor.flightData.map { Point.fromLngLat(it.lng, it.lat) })
//        }
      }
    }

    viewModelScope.launch {
      mapboxInteractor.routeLocationFlow().collect {
        _routeData.postValue(it)
      }
    }
  }
}