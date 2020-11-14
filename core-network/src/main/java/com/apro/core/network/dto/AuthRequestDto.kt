package com.apro.core.network.dto

import com.google.gson.annotations.SerializedName

data class AuthRequestDto(
  @SerializedName("name") val name: String,
  @SerializedName("email") val email: String,
  @SerializedName("password") val password: String,
  @SerializedName("password_confirmation") val passwordConfirmation: String = password
)
