package com.apro.paraflight.ui.flight

data class FlightModel(
  val lon: Double,
  val lat: Double,
  val alt: Double,
  val speed: Float,
  val dist: Double? = null,
  val duration: Long? = null
)