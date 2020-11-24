package com.apro.core.network.dto.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
  @SerializedName("id") val id: Long,
  @SerializedName("name") val name: String,
  @SerializedName("dt") val dt: Long,
  @SerializedName("coord") val coord: CoordDto,
  @SerializedName("weather") val weather: List<WeatherDto>,
  @SerializedName("base") val base: String,
  @SerializedName("main") val main: MainDto,
  @SerializedName("visibility") val visibility: Int,
  @SerializedName("wind") val wind: WindDto,
  @SerializedName("clouds") val clouds: CloudsDto
)
