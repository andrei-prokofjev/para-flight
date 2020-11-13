package com.apro.paraflight.ui.main

import com.apro.paraflight.ui.mapbox.MapboxInteractor
import com.apro.paraflight.ui.mapbox.MapboxSettings
import javax.inject.Inject

class MainInteractorImpl @Inject constructor(
  val mapboxInteractor: MapboxInteractor
) : MainInteractor {
  init {
    println(">>> initaaa$")

  }

  override fun start() {
    println(">>> start$")
    mapboxInteractor.uiSettings = MapboxSettings(
      zoom = 50.0
    )
  }
}