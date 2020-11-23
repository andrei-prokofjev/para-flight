package com.apro.core.network.dto

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
  @SerializedName("message") val message: String,
  @SerializedName("client") val client: String,
)