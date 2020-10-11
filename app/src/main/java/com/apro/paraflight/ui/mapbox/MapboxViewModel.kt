package com.apro.paraflight.ui.mapbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapboxViewModel : ViewModel() {


  private val _data = MutableLiveData<String>()
  val data: LiveData<String> = _data

}