package com.apro.paraflight.ui.flight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.navigation.AppRouter
import com.apro.core.ui.BaseViewModel
import com.apro.paraflight.ui.mapbox.MapboxInteractor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FlightScreenViewModel @Inject constructor(
  val appRouter: AppRouter,
  private val flightInteractor: FlightInteractor,
  private val mapboxInteractor: MapboxInteractor
) : BaseViewModel() {

  private val _flightData = MutableLiveData<FlightScreenModel>()
  val flight: LiveData<FlightScreenModel> = _flightData

  init {
    flightInteractor.init()

    viewModelScope.launch {
      flightInteractor.updateLocationFlow().collect {
        _flightData.postValue(it.toFlightScreenModel())
      }
    }
  }

  fun onLayerClick() {
    mapboxInteractor.changeMapStyle()
  }

  override fun onCleared() {
    flightInteractor.clear()
  }
}