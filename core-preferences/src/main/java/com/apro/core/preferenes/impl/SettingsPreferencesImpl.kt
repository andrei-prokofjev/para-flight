package com.apro.core.preferenes.impl

import android.app.Application
import android.content.Context
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.util.Speed
import com.apro.core.util.kilometersPerHour
import javax.inject.Inject


class SettingsPreferencesImpl @Inject constructor(
  app: Application
) : SettingsPreferences {

  private val prefs by lazy { app.getSharedPreferences(PREFS, Context.MODE_PRIVATE) }

  override var takeOffSpeed: Int
    get() {
      val speed = prefs.getInt(TAKE_OFF_SPEED, DEFAULT_TAKE_OFF_SPEED)
      return speed.kilometersPerHour.convertTo(Speed.MetersPerSecond).amount.toInt()
    }
    set(value) {
      prefs.edit().putInt(TAKE_OFF_SPEED, value).apply()
    }

  override var takeOffAltDiff: Int
    get() = prefs.getInt(TAKE_OFF_ALT_DIFF, DEFAULT_TAKE_OFF_ALT_DIFF)
    set(value) {
      prefs.edit().putInt(TAKE_OFF_ALT_DIFF, value).apply()
    }

  override var minFlightSpeed: Int
    get() {
      val speed = prefs.getInt(MIN_FLIGHT_SPEED, DEFAULT_MIN_FLIGHT_SPEED)
      return speed.kilometersPerHour.convertTo(Speed.MetersPerSecond).amount.toInt()
    }
    set(value) {
      prefs.edit().putInt(MIN_FLIGHT_SPEED, value).apply()
    }

  override var voiceGuidance: Boolean
    get() = prefs.getBoolean(VOICE_GUIDANCE, DEFAULT_VOICE_GUIDANCE)
    set(value) {
      prefs.edit().putBoolean(VOICE_GUIDANCE, value).apply()
    }

  override var timeNotificationInterval: Long
    get() = prefs.getLong(TIME_NOTIFICATION_INTERVAL, DEFAULT_TIME_NOTIFICATION_INTERVAL)
    set(value) {
      prefs.edit().putLong(TIME_NOTIFICATION_INTERVAL, value).apply()
    }

  override var units: SettingsPreferences.Units
    get() = SettingsPreferences.Units.values()[prefs.getInt(UNITS, DEFAULT_UNITS)]
    set(value) {
      prefs.edit().putInt(UNITS, value.ordinal).apply()
    }

  private companion object {
    const val PREFS = "settings_preferences"

    const val DEFAULT_TAKE_OFF_SPEED = 20
    const val TAKE_OFF_SPEED = "take_off_speed"

    const val DEFAULT_TAKE_OFF_ALT_DIFF = 6
    const val TAKE_OFF_ALT_DIFF = "take_off_alt_diff"

    const val DEFAULT_VOICE_GUIDANCE = true
    const val VOICE_GUIDANCE = "voice_guidance"

    const val DEFAULT_TIME_NOTIFICATION_INTERVAL = 5 * 60_0000L
    const val TIME_NOTIFICATION_INTERVAL = "time_notification_interval"

    const val DEFAULT_MIN_FLIGHT_SPEED = 3
    const val MIN_FLIGHT_SPEED = "min_flight_speed"

    val DEFAULT_UNITS = SettingsPreferences.Units.METRIC.ordinal
    const val UNITS = "units"
  }
}