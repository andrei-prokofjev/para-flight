package com.apro.core.preferenes.api

interface SettingsPreferences {

  var takeOffSpeed: Int

  var takeOffAltDiff: Int

  var voiceGuidance: Boolean

  companion object {
    const val MIN_TAKE_OFF_SPEED = 5
    const val MAX_TAKE_OFF_SPEED = 25

    const val MIN_TAKE_OFF_ALT_DIFF = 0
    const val MAX_TAKE_OFF_ALT_DIFF = 15
  }
}