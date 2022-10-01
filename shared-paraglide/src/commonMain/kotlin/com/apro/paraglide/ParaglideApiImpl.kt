package com.apro.paraglide

import com.apro.paraglide.storage.SessionDataStorage
import io.ktor.client.plugins.logging.*

class ParaglideApiImpl(
  private val baseUrl: String,
  private val logLevel: LogLevel,
  private val sessionDataStorage: SessionDataStorage,
) : ParaglideApi {

  override suspend fun register() {
    TODO("Not yet implemented")
  }

  override suspend fun login() {
    TODO("Not yet implemented")
  }
}