package com.apro.controller.dto.auth

import com.google.gson.annotations.SerializedName

data class RegisterRequestDto(
  @SerializedName("uuid") val uuid: String
)
