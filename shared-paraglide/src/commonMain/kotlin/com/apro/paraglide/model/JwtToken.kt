package com.apro.paraglide.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class JwtToken(
  @SerialName("token") val token: String,
  @SerialName("expiration") val expiration: Instant,
)
