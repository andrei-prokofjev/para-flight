package com.apro.core.db.model

data class FlightPointModel(
  val time: Long,
  val latitude: Double,
  val longitude: Double,
  val altitude: Double
)