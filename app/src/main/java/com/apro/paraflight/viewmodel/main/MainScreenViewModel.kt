package com.apro.paraflight.viewmodel.main

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.db.api.data.store.FlightsStore
import com.apro.core.db.api.di.DatabaseApi
import com.apro.core.model.FlightPointModel
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core_ui.BaseViewModel
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
  private val mapboxPreferences: MapboxPreferences,
  private val flightsStore: FlightsStore,
  private val databaseApi: DatabaseApi
) : BaseViewModel() {

  private val points = mutableListOf<Location>()

  private val _style = MutableLiveData<String>()
  val style: LiveData<String> = _style


  private val _toast = MutableLiveData<String>()
  val toast: LiveData<String> = _toast

  private val _locationData = MutableLiveData<Location>()
  val locationData: LiveData<Location> = _locationData

  private val _line = MutableLiveData<List<FlightPointModel>>()
  val line: LiveData<List<FlightPointModel>> = _line

  fun updateLocation(location: Location) {
    _locationData.postValue(location)
    points.add(location)

    if (points.size > 30) {
      viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        locationData.value?.let {
          val list = points.map { FlightPointModel(it.time, it.latitude, it.longitude, it.altitude) }
          flightsStore.insertFlightPoints(list)
          points.clear()
        }
      }
    }
  }


  fun onSettingsClick() {
    _toast.postValue("settings development in progress")
  }

  fun onNearMeClick() {
    _toast.postValue("near me development in progress")
  }


  fun onLayerClick() {
    val nextStyle = (mapboxPreferences.mapStyle.ordinal + 1) % MapboxPreferences.MapStyle.values().size
    val mapStyle = MapboxPreferences.MapStyle.values()[nextStyle]
    mapboxPreferences.mapStyle = mapStyle
    _style.postValue(getStyle(mapStyle))
  }

  fun onCompassClick() {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      databaseApi.cleaner().clearAll()
    }
  }

  fun onShareClick() {

    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {

      val points = flightsStore.getFlightPoints()

      _line.postValue(points)

      points.forEach {
        println(">>> $it")
      }
    }


  }

  fun onFlightClick() {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      locationData.value?.let {
        val point = FlightPointModel(it.time, it.latitude, it.longitude, it.altitude)
        flightsStore.insertFlightPoints(listOf(point))
      }
    }
  }


  fun getStyle(mapStyle: MapboxPreferences.MapStyle) =
    when (mapStyle) {
      MapboxPreferences.MapStyle.SATELLITE -> Style.SATELLITE
      MapboxPreferences.MapStyle.MAPBOX_STREETS -> Style.MAPBOX_STREETS
      MapboxPreferences.MapStyle.LIGHT -> Style.LIGHT
    }

}