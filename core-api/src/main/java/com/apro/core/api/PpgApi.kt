package com.apro.core.api

import com.apro.controller.PpgController
import com.apro.controller.dto.auth.LoginRequestDto
import com.apro.controller.dto.auth.RegisterRequestDto

class PpgApi(private val controller: PpgController) {

  suspend fun register(uuid: String) = controller.register(RegisterRequestDto(uuid)).model()

  suspend fun login(uuid: String) = controller.login(LoginRequestDto(uuid)).model()


}
