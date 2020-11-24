package com.apro.core.preferenes.impl

import android.app.Application
import android.content.Context
import com.apro.core.preferenes.api.UserProfilePreferences
import javax.inject.Inject

class UserProfilePreferencesImpl @Inject constructor(
  app: Application

) : UserProfilePreferences {

  private val prefs by lazy { app.getSharedPreferences(PREFS, Context.MODE_PRIVATE) }

  override var uuid: String?
    get() = prefs.getString(UUID, null)
    set(value) {
      prefs.edit().putString(UUID, value).apply()
    }
  override var nickname: String?
    get() = prefs.getString(NICKNAME, null)
    set(value) {
      prefs.edit().putString(NICKNAME, value).apply()
    }

  private companion object {
    const val PREFS = "user_profile_preferences"

    const val UUID = "uuid"
    const val NICKNAME = "nickname"
  }
}