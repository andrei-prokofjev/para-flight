package com.apro.controller.dto.auth

import com.google.gson.annotations.SerializedName

data class LoginRequestDto(
  @SerializedName("uuid") val uuid: String
)