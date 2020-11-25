package com.apro.controller

import com.apro.controller.dto.weather.WeatherResponseDto
import retrofit2.http.POST
import retrofit2.http.Query

interface WeatherController {

  @POST("weather")
  suspend fun weatherByCity(@Query("q") city: String): WeatherResponseDto

  @POST("weather")
  suspend fun weatherByLocation(@Query("lat") lat: Double, @Query("lon") lon: Double): WeatherResponseDto
}