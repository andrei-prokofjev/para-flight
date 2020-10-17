package com.apro.paraflight.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apro.core_ui.BaseViewModel
import com.apro.paraflight.preferences.api.MapboxPreferences
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
  val mapboxPreferences: MapboxPreferences

) : BaseViewModel() {


  private val _style = MutableLiveData<String>()
  val style: LiveData<String> = _style


  private val _toast = MutableLiveData<String>()
  val toast: LiveData<String> = _toast


  fun onSettingsClick() {
    _toast.postValue("settings developing in progress")
  }

  fun onNearMeClick() {
    _toast.postValue("near me developing in progress")
  }

  fun onMyLocationClick() {
    _toast.postValue("my location developing in progress")
  }

  fun onLayerClick() {
    val nextStyle = (mapboxPreferences.mapStyle.ordinal + 1) % MapboxPreferences.MapStyle.values().size
    val mapStyle = MapboxPreferences.MapStyle.values()[nextStyle]
    mapboxPreferences.mapStyle = mapStyle
    _style.postValue(mapStyle.style)
  }

  fun onCompassClick() {
    _toast.postValue("compass developing in progress")
  }


}