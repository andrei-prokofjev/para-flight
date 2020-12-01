package com.apro.core.api

import com.apro.controller.MapboxController

class MapboxApi(private val controller: MapboxController) {

  suspend fun getTterrainRgb(xy: Pair<Int, Int>, zoom: Int) {
    controller.terrainRgb(zoom, xy.first, xy.second)

  }
}

