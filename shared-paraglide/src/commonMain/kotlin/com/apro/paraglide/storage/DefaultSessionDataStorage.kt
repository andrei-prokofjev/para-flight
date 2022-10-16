package com.malwarebytes.antimalware.cloud.nebula.storage

import com.apro.paraglide.model.JwtToken
import com.apro.paraglide.storage.SessionDataStorage
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.datetime.toInstant

private const val KEY_USER_ID = "user_id"
private const val KEY_AUTH_TOKEN = "auth_token"
private const val KEY_AUTH_TOKEN_EXPIRATION = "auth_token_expiration"
private const val KEY_REFRESH_TOKEN = "refresh_token"
private const val KEY_REFRESH_TOKEN_EXPIRATION = "refresh_token_expiration"

internal class DefaultSessionDataStorage(
  private val settings: Settings = Settings()
) : SessionDataStorage {

  override var userId: String?
    get() = settings.getStringOrNull(KEY_USER_ID)
    set(value) {
      settings[KEY_USER_ID] = value
    }

  override var authToken: String?
    get() = settings.getStringOrNull(KEY_AUTH_TOKEN)
    set(value) {
      settings[KEY_AUTH_TOKEN] = value
    }

  override var refreshToken: JwtToken?
    get() {
      return JwtToken(
        token = settings.getStringOrNull(KEY_REFRESH_TOKEN) ?: return null,
        expiration = settings.getStringOrNull(KEY_REFRESH_TOKEN_EXPIRATION)
          ?.ifBlank { null }
          ?.toInstant()
          ?: return null
      )
    }
    set(value) {
      settings[KEY_REFRESH_TOKEN] = value?.token
      settings[KEY_REFRESH_TOKEN_EXPIRATION] = value?.expiration.toString()
    }

  override fun clear() {
    authToken = null
    refreshToken = null
  }
}