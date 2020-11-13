package com.apro.paraflight.ui.main

import com.apro.paraflight.ui.mapbox.MapboxInteractor
import javax.inject.Inject

class MainInteractorImpl @Inject constructor(
  val mapboxInteractor: MapboxInteractor
) : MainInteractor