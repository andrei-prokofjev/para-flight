package com.apro.paraflight.viewmodel

import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core.ui.BaseViewModel
import com.apro.core.util.event.EventBus
import com.apro.paraflight.events.MyLocationEvent
import com.apro.paraflight.ui.screen.Screens
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
  val appRouter: Router,
  private val eventBus: EventBus,
  private val mapboxPreferences: MapboxPreferences
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
    val nextStyle = (mapboxPreferences.mapStyle.ordinal + 1) % MapboxPreferences.MapStyle.values().size
    val mapStyle = MapboxPreferences.MapStyle.values()[nextStyle]
    mapboxPreferences.mapStyle = mapStyle
  }

  fun onMyLocationClick() {
    eventBus.send(MyLocationEvent())
  }

}