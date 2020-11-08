package com.apro.paraflight.ui.mapbox

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.network.Utils
import com.apro.core.preferenes.api.MapboxPreferences
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
  private val mapboxInteractor: MapboxInteractor,
  private val mapboxPreferences: MapboxPreferences
) : BaseViewModel() {

  private val _styleData = MutableLiveData<String>()
  val styleData: LiveData<String> = _styleData

  private val _liveLocationData = MutableLiveData<Location>()
  val locationData: LiveData<Location> = _liveLocationData

  private val _routeData = MutableLiveData<List<Point>>()
  val routeData: LiveData<List<Point>> = _routeData

  // <position, duration>
  private val _myCurrentPosition = MutableLiveData<Pair<CameraUpdate, Int>>()
  val myCurrentPosition: LiveData<Pair<CameraUpdate, Int>> = _myCurrentPosition

  private val _uiSettingsData = MutableLiveData<MapboxSettings>()
  val uiSettingsData: LiveData<MapboxSettings> = _uiSettingsData


  init {
    viewModelScope.launch {
      mapboxPreferences.mapStyleFlow().collect { _styleData.postValue(it.style) }
    }

    viewModelScope.launch {
      mapboxInteractor.lastLocationFlow().collect {
        val position = CameraPosition.Builder()
          .target(LatLng(it.latitude, it.longitude))
          .build()

        _myCurrentPosition.postValue(CameraUpdateFactory.newCameraPosition(position) to 500)
      }
    }

    viewModelScope.launch {
      mapboxInteractor.locationUpdatesFlow().collect {
        _liveLocationData.postValue(it)
      }
    }

    viewModelScope.launch {
      mapboxInteractor.uiSettingsFlow().collect {
        _uiSettingsData.postValue(it)

        val a = Utils.getMACAddress("wlan0");
        val b = Utils.getMACAddress("eth0");
        val c = Utils.getIPAddress(true); // IPv4
        val d = Utils.getIPAddress(false); // IPv6

        println(">>> $a")
        println(">>> $b")
        println(">>> $c")
        println(">>> $d")
      }
    }

  }


}