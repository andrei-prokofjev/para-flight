package com.apro.controller.dto.weather

import com.google.gson.annotations.SerializedName

data class PrecipitationDto(
  @SerializedName("1h") val oneHour: Int,
  @SerializedName("3h") val threeHours: Int
)