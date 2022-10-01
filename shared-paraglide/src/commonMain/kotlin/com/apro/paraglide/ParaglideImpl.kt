package com.apro.paraglide

import com.apro.paraglide.model.Tokens
import com.apro.paraglide.storage.SessionDataStorage
import com.malwarebytes.antimalware.cloud.nebula.storage.DefaultSessionDataStorage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ParaglideImpl(
  private val baseUrl: String,
  private val logLevel: LogLevel,
  private val sessionDataStorage: SessionDataStorage,

  ) : Paraglide {

  override val api: ParaglideApi

  init {
    val client = HttpClient {
      expectSuccess = true
      install(Logging) {

        logger = object : Logger {
          override fun log(message: String) {
            println(">>> $message")
          }

        }
        level = logLevel
      }
      install(ContentNegotiation) {
        json(json)
      }
      install(UserAgent) {
        agent = "Apro${Platform.name}ParaglideAgent/${Platform.version}"
      }
      install(Auth) {
        bearer {
          loadTokens {
            val authToken = sessionDataStorage.authToken ?: return@loadTokens null
            val refreshToken = sessionDataStorage.refreshToken ?: return@loadTokens null
            BearerTokens(authToken.token, refreshToken.token)
          }
          refreshTokens {
            val tokens = client.get("${baseUrl}api/v1/auth/refresh") {
              headers {
                append(HttpHeaders.Authorization, "Bearer ${sessionDataStorage.refreshToken?.token}")
              }
            }.body<Tokens>()
            sessionDataStorage.authToken = tokens.authToken
            sessionDataStorage.refreshToken = tokens.refreshToken
            BearerTokens(tokens.authToken.token, tokens.refreshToken.token)
          }
        }
      }
    }

    api = ParaglideApiImpl(
      baseUrl = baseUrl,
      logLevel = logLevel,
      sessionDataStorage = sessionDataStorage
    )
  }

  class Builder {
    private var baseUrl: String? = null
    private var logLevel: LogLevel = LogLevel.NONE
    private var sessionDataStorage: SessionDataStorage? = null

    fun baseUrl(url: String) = apply { this.baseUrl = url }
    fun logLevel(level: LogLevel) = apply { this.logLevel = level }
    fun sessionDataStorage(storage: SessionDataStorage) = apply { this.sessionDataStorage = storage }

    fun build(): ParaglideImpl {
      val baseUrl = checkNotNull(this.baseUrl) { "Base url must be set" }
      return ParaglideImpl(
        baseUrl = baseUrl,
        logLevel = logLevel,
        sessionDataStorage = sessionDataStorage ?: DefaultSessionDataStorage(),
      )
    }
  }
}

val json = Json {
  ignoreUnknownKeys = true
  prettyPrint = true
  isLenient = true
}