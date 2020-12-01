package com.apro.controller

import retrofit2.http.GET
import retrofit2.http.Path

interface MapboxController {

  @GET("mapbox.terrain-rgb/{zoom}/{x}/{y}@2x.pngraw")
  suspend fun terrainRgb(@Path("zoom") zoom: Int, @Path("x") x: Int, @Path("y") y: Int)


}