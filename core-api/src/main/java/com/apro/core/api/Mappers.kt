package com.apro.core.api

import com.apro.controller.dto.auth.LoginResponseDto
import com.apro.controller.dto.auth.RegisterResponseDto
import com.apro.controller.dto.weather.*
import com.apro.core.model.*


fun RegisterResponseDto.model() = PpgRegisterModel(
  nickname = nickname,
  message = message
)

fun LoginResponseDto.model() = PpgLoginModel(
  nickname = client,
  message = message
)

fun WeatherResponseDto.model() = WeatherDataModel(
  id = id,
  name = name,
  time = dt,
  location = coord.model(),
  weather = weather.map { it.model() },
  main = main.model(),
  visibility = visibility,
  wind = wind.model(),
  clouds = clouds.model(),
  rain = rain?.model(),
  snow = snow?.model()
)

fun CoordDto.model() = LatLngModel(
  latitude = lat.toDouble(),
  longitude = lon.toDouble()
)

fun WeatherDto.model() = WeatherModel(
  id = id,
  main = main,
  description = description,
  icon = icon
)

fun MainDto.model() = MainModel(
  temp = temp,
  feelsLike = feelsLike,
  tempMin = tempMin,
  tempMax = tempMax,
  pressure = pressure,
  humidity = humidity,
  seaLevelPressure = seaLevelPressure,
  groundLevelPressure = grndLevelPressure
)

fun WindDto.model() = WindModel(
  speed = speed,
  deg = deg,
  gust = gust
)

fun CloudsDto.model() = CloudsModel(
  all = all
)

fun PrecipitationDto.model() = PrecipitationModel(
  oneHour = oneHour,
  threeHours = threeHours
)



