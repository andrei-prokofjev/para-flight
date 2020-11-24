package com.apro.core.network.api

import com.apro.core.network.dto.weather.WeatherResponseDto
import retrofit2.http.POST
import retrofit2.http.Query

interface WeatherApi {

  @POST("weather")
  suspend fun weatherByCity(@Query("q") city: String): WeatherResponseDto

  @POST("weather")
  suspend fun weatherByLocation(@Query("lat") lat: Double, @Query("lon") lon: Double): WeatherResponseDto

}