package com.apro.core.model.weather

data class WeatherDataModel(
  val id: Long,
  val name: String,
  val time: Long,
  val location: CoordModel,
  val weather: List<WeatherModel>,
  val main: MainModel,
  val visibility: Int,
  val wind: WindModel,
  val clouds: CloudsModel,
  val rain: PrecipitationModel?,
  val snow: PrecipitationModel?
)