package com.apro.paraglide

import com.apro.paraglide.model.ErrorMessage
import com.apro.paraglide.storage.SessionDataStorage
import com.malwarebytes.antimalware.cloud.nebula.storage.DefaultSessionDataStorage
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ParaglideImpl(
  private val baseUrl: String,
  private val logLevel: LogLevel,
  private val sessionDataStorage: SessionDataStorage,

  ) : Paraglide {

  override val api: ParaglideApi
  override val isSessionValid: Boolean
    get() = sessionDataStorage.authToken != null

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
      HttpResponseValidator {
        handleResponseExceptionWithRequest { exception, _ ->
          when (exception) {
            is ClientRequestException -> {
              val error = json.decodeFromString<ErrorMessage>(exception.response.bodyAsText())
              throw Exception(error.message)
            }
          }
        }
      }
    }

    api = ParaglideApiImpl(
      client = client,
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