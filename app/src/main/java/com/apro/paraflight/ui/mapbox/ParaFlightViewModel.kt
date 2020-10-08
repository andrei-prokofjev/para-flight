package com.apro.paraflight.ui.mapbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ParaFlightViewModel : ViewModel() {

  private val _text = MutableLiveData("map fragment")

  val text: LiveData<String> = _text
}