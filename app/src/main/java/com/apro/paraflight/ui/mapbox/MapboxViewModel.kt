package com.apro.paraflight.ui.mapbox

import Speed
import SpeedUnit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apro.core_ui.BaseViewModel
import com.mapbox.android.core.location.LocationEngineResult
import com.microsoft.appcenter.analytics.Analytics
import metersPerSecond

class MapboxViewModel : BaseViewModel() {

  private val speedMeter = Meter.Speed(SpeedUnit.KM_PER_HOUR)

  private val _speed = MutableLiveData<Int>()
  val speed: LiveData<Int> = _speed

  private val _altitude = MutableLiveData<Int>()
  val altitude: LiveData<Int> = _altitude


  fun setResult(result: LocationEngineResult) {

    result.lastLocation?.let {
      speedMeter.setSpeed(it.speed)
      _speed.postValue(it.speed.metersPerSecond.convertTo(Speed.KilometerPerHour).amount.toInt())
      _altitude.postValue(it.altitude.toInt())
    }
  }

  fun onSpeedMeterClick() {
    Analytics.trackEvent("On Speed Meter Click")
    speedMeter.setStyle(MeterStyle.Default)
  }

  fun onAltitudeClick() {
    Analytics.trackEvent("On Altitude Meter Click")

  }
}