package com.apro.paraflight.ui.flight

data class FlightDataModel(
  val lon: Double,
  val lat: Double,
  val alt: Double,
  val speed: Float,
  val dist: Double,
  val duration: Long)