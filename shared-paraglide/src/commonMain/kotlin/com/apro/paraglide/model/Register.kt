package com.apro.paraglide.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
  @SerialName("token") val token: String
)

@Serializable
data class RegisterRequest(
  @SerialName("user_name") val userName: String
)
