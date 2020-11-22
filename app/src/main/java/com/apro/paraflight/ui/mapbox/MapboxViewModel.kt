package com.apro.paraflight.ui.mapbox

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core.ui.BaseViewModel
import com.mapbox.geojson.Point
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

  private val _mapboxSettingsData = MutableLiveData<MapboxSettings>()
  val mapboxSettingsData: LiveData<MapboxSettings> = _mapboxSettingsData


  init {
    viewModelScope.launch {
      mapboxPreferences.mapStyleFlow().collect { _styleData.postValue(it.style) }
    }

    viewModelScope.launch {
      mapboxInteractor.lastLocationFlow().collect {
        _liveLocationData.postValue(it)
      }
    }

    viewModelScope.launch {
      mapboxInteractor.locationUpdatesFlow().collect {
        _liveLocationData.postValue(it)
      }
    }

    viewModelScope.launch {
      mapboxInteractor.mapboxSettingsFlow().collect {
        _mapboxSettingsData.postValue(it)
      }
    }

  }


}