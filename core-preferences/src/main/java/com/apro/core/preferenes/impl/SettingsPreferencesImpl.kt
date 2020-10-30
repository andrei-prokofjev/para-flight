package com.apro.core.preferenes.impl

import android.app.Application
import android.content.Context
import com.apro.core.preferenes.api.SettingsPreferences
import javax.inject.Inject


class SettingsPreferencesImpl @Inject constructor(
  app: Application
) : SettingsPreferences {

  private val prefs by lazy { app.getSharedPreferences(PREFS, Context.MODE_PRIVATE) }

  override var takeOffSpeed: Int
    get() = prefs.getInt(TAKE_OFF_SPEED, DEFAULT_TAKE_OFF_SPEED)
    set(value) {
      prefs.edit().putInt(TAKE_OFF_SPEED, value).apply()
    }

  override var takeOffAltDiff: Int
    get() = prefs.getInt(TAKE_OFF_ALT_DIFF, DEFAULT_TAKE_OFF_ALT_DIFF)
    set(value) {
      prefs.edit().putInt(TAKE_OFF_ALT_DIFF, value).apply()
    }

  override var voiceGuidance: Boolean
    get() = prefs.getBoolean(VOICE_GUIDANCE, true)
    set(value) {
      prefs.edit().putBoolean(VOICE_GUIDANCE, value).apply()
    }

  override var timeNotificationInterval: Long
    get() = prefs.getLong(TIME_NOTIFICATION_INTERVAL, DEFAULT_TIME_NOTIFICATION_INTERVAL)
    set(value) {
      prefs.edit().putLong(TIME_NOTIFICATION_INTERVAL, value).apply()
    }

  private companion object {
    const val PREFS = "settings_preferences"

    const val DEFAULT_TAKE_OFF_SPEED = 20
    const val TAKE_OFF_SPEED = "take_off_speed"

    const val DEFAULT_TAKE_OFF_ALT_DIFF = 6
    const val TAKE_OFF_ALT_DIFF = "take_off_alt_diff"

    const val VOICE_GUIDANCE = "voice_guidance"

    const val DEFAULT_TIME_NOTIFICATION_INTERVAL = 5L
    const val TIME_NOTIFICATION_INTERVAL = "time_notification_interval"
  }
}