package com.apro.core.network.dto.weather

import com.google.gson.annotations.SerializedName

data class CoordDto(
  @SerializedName("lon") val lon: Float,
  @SerializedName("lat") val lat: Float
)