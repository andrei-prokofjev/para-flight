package com.apro.controller

import com.apro.controller.dto.auth.LoginRequestDto
import com.apro.controller.dto.auth.LoginResponseDto
import com.apro.controller.dto.auth.RegisterRequestDto
import com.apro.controller.dto.auth.RegisterResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface PpgController {

  @POST("/api/auth/client/register")
  suspend fun register(@Body requestDto: RegisterRequestDto): RegisterResponseDto

  @POST("/api/auth/client/login")
  suspend fun login(@Body requestDto: LoginRequestDto): LoginResponseDto
}