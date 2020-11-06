package com.apro.paraflight.ui.main

import com.apro.core.location.engine.api.LocationEngine
import com.apro.core.navigation.AppRouter
import com.apro.core.ui.BaseViewModel
import com.apro.paraflight.ui.Screens
import com.apro.paraflight.ui.mapbox.MapboxInteractor
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
  val appRouter: AppRouter,
  private val mapboxInteractor: MapboxInteractor
) : BaseViewModel() {


  fun onSettingsClick() {
    appRouter.navigateTo(Screens.settings())
  }

  fun onLogbookClick() {
    appRouter.navigateTo(Screens.logbook())
  }

  fun onPreflightClick(locationEngine: LocationEngine) {
    //appRouter.navigateTo(Screens.preflight())
    appRouter.navigateTo(Screens.flight(locationEngine))
  }

  fun onLayerClick() {
    mapboxInteractor.changeMapStyle()
  }

  fun onMyLocationClick() {
    mapboxInteractor.navigateToCurrentPosition()
  }
}