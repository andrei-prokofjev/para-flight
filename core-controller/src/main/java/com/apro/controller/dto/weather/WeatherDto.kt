package com.apro.controller.dto.weather

import com.google.gson.annotations.SerializedName

data class WeatherDto(
  @SerializedName("id") val id: Long,
  @SerializedName("main") val main: String,
  @SerializedName("description") val description: String,
  @SerializedName("icon") val icon: String
)
