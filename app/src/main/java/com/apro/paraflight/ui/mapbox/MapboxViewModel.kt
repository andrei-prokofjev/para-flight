package com.apro.paraflight.ui.mapbox

import Speed
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.apro.core_ui.BaseViewModel
import com.mapbox.android.core.location.LocationEngineResult
import com.microsoft.appcenter.analytics.Analytics
import kotlinx.coroutines.launch
import metersPerSecond

class MapboxViewModel : BaseViewModel() {

  private val _speed = MutableLiveData<Int>()
  val speed: LiveData<Int> = _speed

  private val _altitude = MutableLiveData<Int>()
  val altitude: LiveData<Int> = _altitude


  fun setResult(result: LocationEngineResult) {

    result.lastLocation?.let {
      _speed.postValue(it.speed.metersPerSecond.convertTo(Speed.KilometerPerHour).amount.toInt())
      _altitude.postValue(it.altitude.toInt())
    }
  }

  fun onSpeedMeterClick() {
    Analytics.trackEvent("On Speed Meter Click")
  }

  fun onAltitudeClick() {
    Analytics.trackEvent("On Altitude Meter Click")
    viewModelScope.launch { }

    liveData<String> { }
  }
}