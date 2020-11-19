package com.apro.paraflight.ui.flight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.location.engine.api.LocationEngine
import com.apro.core.model.FlightModel
import com.apro.core.navigation.AppRouter
import com.apro.core.ui.BaseViewModel
import com.apro.paraflight.ui.mapbox.CameraMode
import com.apro.paraflight.ui.mapbox.MapboxInteractor
import com.apro.paraflight.ui.mapbox.MapboxSettings
import com.apro.paraflight.ui.mapbox.RenderMode
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


  private val _mapboxSettingsData = MutableLiveData<MapboxSettings>()
  val mapboxSettingsData: LiveData<MapboxSettings> = _mapboxSettingsData

  init {
    mapboxInteractor.requestLocationUpdates(locationEngine)

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

    viewModelScope.launch {
      mapboxInteractor.mapboxSettingsFlow().collect {
        _mapboxSettingsData.postValue(it)
      }
    }
  }

  fun updateSettings(settings: MapboxSettings) {
    mapboxInteractor.mapboxSettings = settings
  }

  fun onLayerClick() {
    mapboxInteractor.changeMapStyle()
  }

  override fun onCleared() {
    flightInteractor.clear()
    mapboxInteractor.removeLocationUpdate()
  }

  fun changeCameraMode() {
    val next = (mapboxInteractor.mapboxSettings.cameraMode.ordinal + 1) % CameraMode.values().size
    updateSettings(mapboxInteractor.mapboxSettings.copy(
      cameraMode = CameraMode.values()[next]
    ))
  }

  fun changeRenderMode() {
    val next = (mapboxInteractor.mapboxSettings.renderMode.ordinal + 1) % RenderMode.values().size
    updateSettings(mapboxInteractor.mapboxSettings.copy(
      renderMode = RenderMode.values()[next]
    ))
  }
}