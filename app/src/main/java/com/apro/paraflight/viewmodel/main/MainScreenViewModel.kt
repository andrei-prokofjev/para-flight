package com.apro.paraflight.viewmodel.main

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core_ui.BaseViewModel
import com.mapbox.mapboxsdk.maps.Style
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
  private val mapboxPreferences: MapboxPreferences
) : BaseViewModel() {


  private val _style = MutableLiveData<String>()
  val style: LiveData<String> = _style


  private val _toast = MutableLiveData<String>()
  val toast: LiveData<String> = _toast

  private val _locationData = MutableLiveData<Location>()
  val locationData: LiveData<Location> = _locationData

  fun updateLocation(location: Location) {
    _locationData.postValue(location)
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
    _toast.postValue("compass development in progress")
  }

  fun onShareClick() {
    _toast.postValue("share development in progress")
  }

  fun onFlightClick() {
    _toast.postValue("flight development in progress")
  }


  fun getStyle(mapStyle: MapboxPreferences.MapStyle) =
    when (mapStyle) {
      MapboxPreferences.MapStyle.SATELLITE -> Style.SATELLITE
      MapboxPreferences.MapStyle.MAPBOX_STREETS -> Style.MAPBOX_STREETS
      MapboxPreferences.MapStyle.LIGHT -> Style.LIGHT
    }

}