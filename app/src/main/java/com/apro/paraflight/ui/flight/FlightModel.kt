package com.apro.paraflight.ui.flight

data class FlightModel(
  val lon: Double,
  val lat: Double,
  val alt: Double,
  val speed: Float,
  val dist: Double = 0.0,
  val duration: Long = 0L
)