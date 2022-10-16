package com.apro.paraglide

import com.apro.paraglide.model.RegisterResponse
import com.apro.paraglide.storage.SessionDataStorage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.utils.io.charsets.*
import io.ktor.utils.io.core.*

class ParaglideApiImpl(
  private val client: HttpClient,
  private val baseUrl: String,
  private val logLevel: LogLevel,
  private val sessionDataStorage: SessionDataStorage,
) : ParaglideApi {

  override suspend fun register(userName: String, password: String) {
    val authBuf = "${userName}:${password}".toByteArray(Charsets.UTF_8).encodeBase64()
    client.get("${baseUrl}api/v1/register") {
      headers { append(HttpHeaders.Authorization, "Basic $authBuf") }
      contentType(ContentType.Application.Json)
    }.body<RegisterResponse>().apply {
      sessionDataStorage.authToken = authToken
    }
  }
}