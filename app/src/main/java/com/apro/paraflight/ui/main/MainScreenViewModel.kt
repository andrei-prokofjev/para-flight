package com.apro.paraflight.ui.main

import androidx.lifecycle.viewModelScope
import com.apro.core.location.engine.api.LocationEngine
import com.apro.core.navigation.AppRouter
import com.apro.core.ui.BaseViewModel
import com.apro.paraflight.DI
import com.apro.paraflight.ui.Screens
import com.apro.paraflight.ui.mapbox.MapboxInteractor
import com.apro.paraflight.ui.mapbox.MapboxSettings
import com.apro.paraflight.util.ResourceProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.PI
import kotlin.math.asinh
import kotlin.math.floor
import kotlin.math.tan

class MainScreenViewModel @Inject constructor(
  val appRouter: AppRouter,
  private val mapboxInteractor: MapboxInteractor,
  private val locationEngine: LocationEngine,
  val resourceProvider: ResourceProvider


) : BaseViewModel() {

  init {

    viewModelScope.launch {
      mapboxInteractor.lastLocationFlow().collect {

        val xy = getXYTile(it.latitude, it.longitude, 1)

        DI.appComponent.mapboxApi().getTterrainRgb(xy, 1)

      }
    }
  }


  fun setSettings(settings: MapboxSettings) {
    mapboxInteractor.mapboxSettings = settings
  }

  fun onSettingsClick() {
    appRouter.navigateTo(Screens.settings())
  }

  fun onLogbookClick() {
    appRouter.navigateTo(Screens.logbook())
  }

  fun onPreflightClick(locationEngine: LocationEngine) {
    appRouter.navigateTo(Screens.flight(locationEngine, MapboxSettings.ReplayFlightMapboxSettings))
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

  fun onAboutClick() {
    appRouter.navigateTo(Screens.about())
  }

  fun getXYTile(lat: Double, lon: Double, zoom: Int): Pair<Int, Int> {
    val latRad = Math.toRadians(lat)
    var xtile = floor((lon + 180) / 360 * (1 shl zoom)).toInt()
    var ytile = floor((1.0 - asinh(tan(latRad)) / PI) / 2 * (1 shl zoom)).toInt()

    if (xtile < 0) {
      xtile = 0
    }
    if (xtile >= (1 shl zoom)) {
      xtile = (1 shl zoom) - 1
    }
    if (ytile < 0) {
      ytile = 0
    }
    if (ytile >= (1 shl zoom)) {
      ytile = (1 shl zoom) - 1
    }

    return Pair(xtile, ytile)
  }


}