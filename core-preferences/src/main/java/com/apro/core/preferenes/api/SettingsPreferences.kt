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

  var windDetectionPoints: Int

  companion object {
    const val MIN_TAKE_OFF_SPEED = 5
    const val MAX_TAKE_OFF_SPEED = 25

    const val MIN_TAKE_OFF_ALT_DIFF = 0
    const val MAX_TAKE_OFF_ALT_DIFF = 15

    const val MIN_MIN_FLIGHT_SPEED = 0
    const val MAX_MIN_FLIGHT_SPEED = 7

    const val MIN_WIND_DETECTION_POINTS = 4
    const val MAX_WIND_DETECTION_POINTS = 40
  }
}