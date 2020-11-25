package com.apro.core.model

data class WeatherDataModel(
  val id: Long,
  val name: String,
  val time: Long,
  val location: LatLngModel,
  val weather: List<WeatherModel>,
  val main: MainModel,
  val visibility: Int,
  val wind: WindModel,
  val clouds: CloudsModel,
  val rain: PrecipitationModel?,
  val snow: PrecipitationModel?
)