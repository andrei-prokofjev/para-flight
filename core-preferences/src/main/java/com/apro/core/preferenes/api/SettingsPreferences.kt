package com.apro.core.preferenes.api

interface SettingsPreferences {

  enum class Units {
    METRIC,
    IMPERIAL
  }

  var takeOffSpeed: Int
  var takeOffAltDiff: Int

  var voiceGuidance: Boolean

  var timeNotificationInterval: Long

  var minFlightSpeed: Int

  var units: Units

  var windDetector: Boolean

  companion object {
    const val MIN_TAKE_OFF_SPEED = 5
    const val MAX_TAKE_OFF_SPEED = 25

    const val MIN_TAKE_OFF_ALT_DIFF = 0
    const val MAX_TAKE_OFF_ALT_DIFF = 15

    const val MIN_MIN_FLIGHT_SPEED = 0
    const val MAX_MIN_FLIGHT_SPEED = 7
  }
}