package com.apro.paraflight.ui.main

import androidx.lifecycle.viewModelScope
import com.apro.core.location.engine.api.LocationEngine
import com.apro.core.navigation.AppRouter
import com.apro.core.network.dto.AuthRequestDto
import com.apro.core.ui.BaseViewModel
import com.apro.paraflight.DI
import com.apro.paraflight.ui.Screens
import com.apro.paraflight.ui.mapbox.MapboxInteractor
import com.apro.paraflight.ui.mapbox.MapboxSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
  val appRouter: AppRouter,
  private val mapboxInteractor: MapboxInteractor,
  private val locationEngine: LocationEngine,


  ) : BaseViewModel() {


  fun setSettings(settings: MapboxSettings) {
    mapboxInteractor.uiSettings = settings
  }

  fun onSettingsClick() {
    appRouter.navigateTo(Screens.settings())
  }

  fun onLogbookClick() {
    //appRouter.navigateTo(Screens.logbook())
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      val response = DI.networkComponent.ppgApi().register(AuthRequestDto(
        name = "andrei",
        email = "andrei.prokofjev@gmail.com",
        password = "12345"
      ))

      println(">>> resp: $response")
    }

  }

  fun onPreflightClick(locationEngine: LocationEngine) {
    appRouter.navigateTo(Screens.flight(locationEngine))
  }

  fun onLayerClick() {
    viewModelScope.launch { mapboxInteractor.changeMapStyle() }
  }

  fun onMyLocationClick() {
    mapboxInteractor.requestLastLocation(locationEngine)
  }

  override fun onCleared() {
    mapboxInteractor.removeLocationUpdate()
  }

}