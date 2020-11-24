package com.apro.core.network.dto.weather

import com.google.gson.annotations.SerializedName

data class CloudsDto(
  @SerializedName("all") val all: Int
)