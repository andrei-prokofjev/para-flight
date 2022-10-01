package com.apro.paraglide.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tokens(
  @SerialName("auth_token") @Contextual val authToken: JwtToken,
  @SerialName("refresh_token") @Contextual val refreshToken: JwtToken,
)
