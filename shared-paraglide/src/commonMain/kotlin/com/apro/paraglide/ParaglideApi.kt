package com.apro.paraglide

import com.apro.paraglide.model.RegisterResponse

interface ParaglideApi {

  suspend fun register(userName: String): RegisterResponse

  suspend fun login()

}