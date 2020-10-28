package com.apro.paraflight.ui.mapbox

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core.ui.BaseViewModel
import com.apro.core.util.event.EventBus
import com.apro.paraflight.events.MyLocationEvent
import com.apro.paraflight.ui.flight.FlightInteractor
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.camera.CameraUpdate
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapboxViewModel @Inject constructor(
  private val mapboxPreferences: MapboxPreferences,
  private val flightInteractor: FlightInteractor
) : BaseViewModel() {


  private val _style = MutableLiveData<String>()
  val style: LiveData<String> = _style

  private val _liveLocationData = MutableLiveData<Location>()
  val locationData: LiveData<Location> = _liveLocationData

  private val _routeData = MutableLiveData<List<Point>>()
  val routeData: LiveData<List<Point>> = _routeData

  private val _cameraPosition = MutableLiveData<Pair<CameraUpdate, Int>>()
  val cameraPosition: LiveData<Pair<CameraUpdate, Int>> = _cameraPosition


  init {
    viewModelScope.launch {
      mapboxPreferences.styleFlow().collect {
        _style.postValue(getStyle(it)
        )
      }
    }
    // update camera position with current location
    viewModelScope.launch {
      EventBus.observeChannel(MyLocationEvent::class).collect {
        _cameraPosition.postValue(it.cameraUpdate to it.duration)
      }
    }
//    // update map
//    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
//      locationEngine.updateLocationFlow().collect {
//        val lastPoint = Point.fromLngLat(it.longitude, it.latitude)
//        // update location
//        _liveLocationData.postValue(it)
//        // save into base
//        val point = LocationPointModel(it.time, it.latitude, it.longitude, it.altitude)
//        routeStore.insertLocationPoint(point)
//        // draw route
//        val route = mutableListOf<Point>()
//        _routeData.value?.let {
//          route.addAll(it)
//          route.add(lastPoint)
//        } ?: route.add(lastPoint)
//
//        _routeData.postValue(route)
//      }
//    }
//    // flight take off
//    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
//      EventBus.observeChannel(StartFlightEvent::class).collect {
//        databaseApi.cleaner().clearAll()
//        _routeData.postValue(emptyList())
//        locationEngine.requestLocationUpdates()
//      }
//    }
//    // flight land
//    viewModelScope.launch {
//      EventBus.observeChannel(StopFlightEvent::class).collect {
//        locationEngine.removeLocationUpdates()
//      }
//    }
  }

  fun getStyle(mapStyle: MapboxPreferences.MapStyle) =
    when (mapStyle) {
      MapboxPreferences.MapStyle.SATELLITE -> Style.SATELLITE
      MapboxPreferences.MapStyle.MAPBOX_STREETS -> Style.MAPBOX_STREETS
      MapboxPreferences.MapStyle.LIGHT -> Style.LIGHT
    }
}