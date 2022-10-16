package com.apro.paraglide.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RegisterResponse(
  @SerialName("auth_token") @Contextual val authToken: String,
)

@Serializable
data class Tokens(
  @SerialName("auth_token") @Contextual val authToken: JwtToken,
  @SerialName("refresh_token") @Contextual val refreshToken: JwtToken,
)

@Serializable
data class JwtToken(
  @SerialName("token") val token: String,
  @SerialName("expiration") val expiration: Instant,
)
