package com.apro.core.network.dto.auth

import com.google.gson.annotations.SerializedName

data class RegisterRequestDto(
  @SerializedName("uuid") val uuid: String,
  @SerializedName("ip_address") val idAddress: String,
)
