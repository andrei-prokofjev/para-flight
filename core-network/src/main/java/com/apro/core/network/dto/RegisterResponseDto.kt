package com.apro.core.network.dto

import com.google.gson.annotations.SerializedName

data class RegisterResponseDto(
  @SerializedName("message") val message: String
)