package com.apro.paraflight.ui.flight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.navigation.AppRouter
import com.apro.core.ui.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FlightScreenViewModel @Inject constructor(
  val appRouter: AppRouter,
  private val flightInteractor: FlightInteractor
) : BaseViewModel() {

  private val _locationData = MutableLiveData<FlightPoint>()
  val locationData: LiveData<FlightPoint> = _locationData

  private val _distData = MutableLiveData(0.0)
  val distData: LiveData<Double> = _distData

  // val dist = TurfMeasurement.distance(route?.get(0), lastPoint, TurfConstants.UNIT_METERS)

  init {
    flightInteractor.init()

    viewModelScope.launch { flightInteractor.updateLocationFlow().collect { _locationData.postValue(it) } }


  }

  override fun onCleared() {
    flightInteractor.clear()
  }
}