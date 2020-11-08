package com.apro.paraflight.ui.flight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.location.engine.api.LocationEngine
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

  private val _flightData = MutableLiveData<FlightScreenModel>()
  val flightData: LiveData<FlightScreenModel> = _flightData

  private val _testData = MutableLiveData<String>()
  val testData: LiveData<String> = _testData

  init {
    mapboxInteractor.requestLocationUpdates(locationEngine)

    viewModelScope.launch {
      flightInteractor.flightDataFlow().collect {
        _flightData.postValue(it.toFlightScreenModel())
      }
    }

    viewModelScope.launch {
      flightInteractor.testFlow.collect {
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