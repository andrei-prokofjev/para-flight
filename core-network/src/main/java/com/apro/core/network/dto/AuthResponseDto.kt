package com.apro.core.network.dto

import com.google.gson.annotations.SerializedName

class AuthResponseDto(
  @SerializedName("message") val message: String,
  @SerializedName("user") val user: UserDto
)