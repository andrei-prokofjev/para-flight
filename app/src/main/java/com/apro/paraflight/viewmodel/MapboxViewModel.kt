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
import com.apro.paraflight.mapbox.FlightLocationEngine
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
  private val flightLocationEngine: FlightLocationEngine
) : BaseViewModel() {

  private val _style = MutableLiveData<String>()
  val style: LiveData<String> = _style

  private val _liveLocationData = MutableLiveData<Location>()
  val locationData: LiveData<Location> = _liveLocationData

  private val _cameraPosition = MutableLiveData<Location>()
  val cameraPosition: LiveData<Location> = _cameraPosition


  init {
    eventBus.send(MyLocationEvent(), 1000)

    // change map stayle
    viewModelScope.launch {
      mapboxPreferences.styleFlow().collect {
        _style.postValue(getStyle(it)
        )
      }
    }
    // update camera postion with cureent location
    viewModelScope.launch {
      EventBus.observeChannel(MyLocationEvent::class).collect {
        flightLocationEngine.getLastLocation { location ->
          location?.let { _cameraPosition.postValue(it) }
        }
      }
    }
    // update map postion and save current positon into db
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      flightLocationEngine.updateLocationFlow().collect { location ->
        _liveLocationData.postValue(location)

        location?.let {
          val point = LocationPointModel(it.time, it.latitude, it.longitude, it.altitude)
          routeStore.insertLocationPoint(point)
        }
      }
    }
    // flight take off
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      EventBus.observeChannel(StartFlightEvent::class).collect {
        databaseApi.cleaner().clearAll()
        flightLocationEngine.requestLocationUpdates()
      }
    }
    // flight land
    viewModelScope.launch {
      EventBus.observeChannel(StopFlightEvent::class).collect {
        flightLocationEngine.removeLocationUpdates()
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
    flightLocationEngine.clear()
    super.onCleared()
  }
}