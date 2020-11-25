package com.apro.controller.dto.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponseDto(
  @SerializedName("message") val message: String,
  @SerializedName("nickname") val nickname: String
)