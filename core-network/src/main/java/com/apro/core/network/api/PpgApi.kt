package com.apro.core.network.api

import com.apro.core.network.dto.AuthRequestDto
import com.apro.core.network.dto.AuthResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface PpgApi {

  @POST("/api/auth/register")
  suspend fun register(@Body requestDto: AuthRequestDto): AuthResponseDto
}