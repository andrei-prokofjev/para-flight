package com.apro.core.model.weather

data class MainModel(
  val temp: Float,
  val feelsLike: Float,
  val tempMin: Float,
  val tempMax: Float,
  val pressure: Float,
  val humidity: Float,
  val seaLevelPressure: Float,
  val groundLevelPressure: Float,
)
