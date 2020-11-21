package com.apro.core.model

data class FlightModel(
  val lng: Double,
  val lat: Double,
  val alt: Double,
  val bearing: Float,
  val groundSpeed: Float,
  val dist: Double? = null,
  val duration: Long? = null,
  val windVector: Pair<Float, Float>? = null,
  val windSpeed: Float? = null,
  val airSpeed: Float? = null,
  val winDirection: Float = 0f
)