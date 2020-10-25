package com.apro.paraflight.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.db.api.data.store.RouteStore
import com.apro.core.db.api.di.DatabaseApi
import com.apro.core.db.model.LocationPointModel
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core.ui.BaseViewModel
import com.apro.core.util.event.EventBus
import com.apro.paraflight.events.MyLocationEvent
import com.apro.paraflight.events.StartFlightEvent
import com.apro.paraflight.events.StopFlightEvent
import com.apro.paraflight.mapbox.FlightLocationEngineImpl
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdate
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapboxViewModel @Inject constructor(
  private val mapboxPreferences: MapboxPreferences,
  private val routeStore: RouteStore,
  private val databaseApi: DatabaseApi,
  eventBus: EventBus,
  private val locationEngine: FlightLocationEngineImpl
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
    // move camera to the current position
    locationEngine.getLastLocation { location ->
      location?.let {
        val position = CameraPosition.Builder()
          .target(LatLng(it.latitude, it.longitude))
          .zoom(12.0)
          .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(position)
        eventBus.send(MyLocationEvent(cameraUpdate, 2500), 1500)
      }
    }

    // change map stayle
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
    // update map
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      locationEngine.updateLocationFlow().collect { location ->
        location?.let {
          // update location
          _liveLocationData.postValue(it)
          // save into base
          val point = LocationPointModel(it.time, it.latitude, it.longitude, it.altitude)
          routeStore.insertLocationPoint(point)
          // draw route
          val lastPoint = Point.fromLngLat(it.longitude, it.latitude)
          val route = mutableListOf<Point>()
          _routeData.value?.let {
            route.addAll(it)
            route.add(lastPoint)
          } ?: route.add(lastPoint)

          _routeData.postValue(route)
        }
      }
    }
    // flight take off
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      EventBus.observeChannel(StartFlightEvent::class).collect {
        databaseApi.cleaner().clearAll()
        locationEngine.requestLocationUpdates()
      }
    }
    // flight land
    viewModelScope.launch {
      EventBus.observeChannel(StopFlightEvent::class).collect {
        locationEngine.removeLocationUpdates()
        _routeData.postValue(emptyList())
      }
    }
  }

  fun getStyle(mapStyle: MapboxPreferences.MapStyle) =
    when (mapStyle) {
      MapboxPreferences.MapStyle.SATELLITE -> Style.SATELLITE
      MapboxPreferences.MapStyle.MAPBOX_STREETS -> Style.MAPBOX_STREETS
      MapboxPreferences.MapStyle.LIGHT -> Style.LIGHT
    }

  override fun onCleared() {
    locationEngine.clear()
    super.onCleared()
  }
}