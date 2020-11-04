package com.apro.core.model

data class FlightModel(
  val lng: Double,
  val lat: Double,
  val alt: Double,
  val speed: Float,
  val dist: Double? = null,
  val duration: Long? = null
)