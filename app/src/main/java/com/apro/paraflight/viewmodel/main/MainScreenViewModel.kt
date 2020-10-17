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


//  fun setResult(result: LocationEngineResult) {
//
//
//  }

  fun onMenuClick() {

  }

  fun nearMeClick() {

  }

  fun myLocationClick() {

  }

  fun onLayerClick() {
    val nextStyle = (mapboxPreferences.mapStyle.ordinal + 1) % MapboxPreferences.MapStyle.values().size
    val mapStyle = MapboxPreferences.MapStyle.values()[nextStyle]
    mapboxPreferences.mapStyle = mapStyle
    _style.postValue(mapStyle.style)
  }


}