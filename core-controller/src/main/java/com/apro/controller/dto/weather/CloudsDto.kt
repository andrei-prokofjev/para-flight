package com.apro.controller.dto.weather

import com.google.gson.annotations.SerializedName

data class CloudsDto(
  @SerializedName("all") val all: Int
)