package com.apro.paraflight.ui.flight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.location.engine.api.LocationEngine
import com.apro.core.location.engine.model.DilutionOfPrecision
import com.apro.core.model.FlightModel
import com.apro.core.navigation.AppRouter
import com.apro.core.ui.BaseViewModel
import com.apro.paraflight.ui.mapbox.MapboxInteractor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FlightScreenViewModel @Inject constructor(
  val appRouter: AppRouter,
  private val flightInteractor: FlightInteractor,
  private val mapboxInteractor: MapboxInteractor,
  locationEngine: LocationEngine
) : BaseViewModel() {

  private val _flightData = MutableLiveData<FlightModel>()
  val flightData: LiveData<FlightModel> = _flightData

  private val _testData = MutableLiveData<String>()
  val testData: LiveData<String> = _testData

  private val _dopData = MutableLiveData<DilutionOfPrecision?>()
  val dopData: LiveData<DilutionOfPrecision?> = _dopData

  init {
    mapboxInteractor.requestLocationUpdates(locationEngine)

    viewModelScope.launch {
      mapboxInteractor.dopFlow().collect {
        _dopData.postValue(it)
      }
    }

    viewModelScope.launch {
      flightInteractor.flightDataFlow().collect {
        _flightData.postValue(it)
      }
    }

    viewModelScope.launch {
      flightInteractor.debugFlow.collect {
        _testData.postValue(it)
      }
    }
  }

  fun onLayerClick() {
    mapboxInteractor.changeMapStyle()
  }

  override fun onCleared() {
    flightInteractor.clear()
    mapboxInteractor.removeLocationUpdate()
  }
}