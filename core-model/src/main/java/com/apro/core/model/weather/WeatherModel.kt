package com.apro.core.model.weather

data class WeatherModel(
  val id: Long,
  val main: String,
  val description: String,
  val icon: String
)
