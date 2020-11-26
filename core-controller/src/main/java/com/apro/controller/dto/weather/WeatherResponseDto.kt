package com.apro.controller.dto.weather

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
  @SerializedName("clouds") val clouds: CloudsDto,
  @SerializedName("rain") val rain: PrecipitationDto?,
  @SerializedName("snow") val snow: PrecipitationDto?
)

data class MainDto(
  @SerializedName("temp") val temp: Float,
  @SerializedName("feels_like") val feelsLike: Float,
  @SerializedName("temp_min") val tempMin: Float,
  @SerializedName("temp_max") val tempMax: Float,
  @SerializedName("pressure") val pressure: Float,
  @SerializedName("humidity") val humidity: Float,
  @SerializedName("sea_level") val seaLevelPressure: Float,
  @SerializedName("grnd_level") val grndLevelPressure: Float
)

data class CoordDto(
  @SerializedName("lon") val lon: Float,
  @SerializedName("lat") val lat: Float
)

data class CloudsDto(
  @SerializedName("all") val all: Int
)

data class WindDto(
  @SerializedName("speed") val speed: Float,
  @SerializedName("deg") val deg: Float,
  @SerializedName("gust") val gust: Float,
)

data class WeatherDto(
  @SerializedName("id") val id: Long,
  @SerializedName("main") val main: String,
  @SerializedName("description") val description: String,
  @SerializedName("icon") val icon: String
)

data class PrecipitationDto(
  @SerializedName("1h") val oneHour: Float,
  @SerializedName("3h") val threeHours: Float
)