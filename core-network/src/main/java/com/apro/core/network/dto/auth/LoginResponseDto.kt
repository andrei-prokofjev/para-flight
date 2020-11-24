package com.apro.core.network.dto.auth

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
  @SerializedName("message") val message: String,
  @SerializedName("client") val client: String,
)