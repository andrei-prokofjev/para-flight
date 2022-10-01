package com.apro.paraglide

import com.apro.paraglide.model.RegisterRequest
import com.apro.paraglide.model.RegisterResponse
import com.apro.paraglide.storage.SessionDataStorage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

class ParaglideApiImpl(
  private val client: HttpClient,
  private val baseUrl: String,
  private val logLevel: LogLevel,
  private val sessionDataStorage: SessionDataStorage,
) : ParaglideApi {

  override suspend fun register(userName: String): RegisterResponse =
    client.post("${baseUrl}api/v1/register") {
      contentType(ContentType.Application.Json)
      setBody(RegisterRequest(userName = userName)
      )
    }
      .body<RegisterResponse>()
      .also { response ->
        println(">>> response: " + response)
      }

  override suspend fun login() {

  }
}