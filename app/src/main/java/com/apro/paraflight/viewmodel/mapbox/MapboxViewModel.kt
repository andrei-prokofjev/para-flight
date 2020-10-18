package com.apro.paraflight.viewmodel.mapbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apro.core.util.Speed
import com.apro.core.util.metersPerSecond
import com.apro.core_ui.BaseViewModel
import com.mapbox.android.core.location.LocationEngineResult

class MapboxViewModel : BaseViewModel() {

  //private val speedMeter = Meter.com.apro.core_util.Speed(com.apro.core_util.SpeedUnit.KM_PER_HOUR)

  private val _speed = MutableLiveData<Int>()

  val speed: LiveData<Int> = _speed
  private val _altitude = MutableLiveData<Int>()

  val altitude: LiveData<Int> = _altitude

  init {
    println(">>>init")
  }


  var locationEngineResult: LocationEngineResult? = null
    set(value) {
      value?.lastLocation?.let {
        //  speedMeter.setSpeed(it.speed)
        _speed.postValue(it.speed.metersPerSecond.convertTo(Speed.KilometerPerHour).amount.toInt())
        _altitude.postValue(it.altitude.toInt())


      }
      field = value
    }


  fun onSpeedMeterClick() {
    //Analytics.trackEvent("On com.apro.core_util.Speed Meter Click")
    // speedMeter.setStyle(MeterStyle.Default)
  }

  fun onAltitudeClick() {
    //Analytics.trackEvent("On Altitude Meter Click")

  }
}