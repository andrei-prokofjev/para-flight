package com.apro.paraglide

interface ParaglideApi {
  suspend fun register(userName: String, password: String)
}