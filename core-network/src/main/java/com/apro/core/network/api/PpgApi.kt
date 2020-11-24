package com.apro.core.network.api

import com.apro.core.network.dto.auth.LoginRequestDto
import com.apro.core.network.dto.auth.LoginResponseDto
import com.apro.core.network.dto.auth.RegisterRequestDto
import com.apro.core.network.dto.auth.RegisterResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface PpgApi {

  @POST("/api/auth/client/register")
  suspend fun register(@Body requestDto: RegisterRequestDto): RegisterResponseDto

  @POST("/api/auth/client/login")
  suspend fun login(@Body requestDto: LoginRequestDto): LoginResponseDto


}