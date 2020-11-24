package com.apro.core.network.dto.weather

import com.google.gson.annotations.SerializedName

data class WindDto(
  @SerializedName("speed") val speed: Float,
  @SerializedName("deg") val deg: Float
)
