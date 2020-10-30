package com.apro.paraflight.ui.main

import com.apro.core.navigation.AppRouter
import com.apro.core.ui.BaseViewModel
import com.apro.paraflight.ui.Screens
import com.apro.paraflight.ui.mapbox.MapboxInteractor
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
  val appRouter: AppRouter,
  private val mapboxInteractor: MapboxInteractor
) : BaseViewModel() {


  fun onProfileClick() {
    appRouter.navigateTo(Screens.profile())
  }

  fun onLogbookClick() {
    appRouter.navigateTo(Screens.logbook())
  }

  fun onPreflightClick() {
    appRouter.navigateTo(Screens.preflight())
  }

  fun onLayerClick() {
    mapboxInteractor.changeMapStyle()
  }

  fun onMyLocationClick() {
    println(">>> my loc$")
    mapboxInteractor.navigateToCurrentPosition()
  }

}