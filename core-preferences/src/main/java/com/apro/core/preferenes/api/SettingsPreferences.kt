package com.apro.core.preferenes.api

import kotlinx.coroutines.flow.Flow

interface SettingsPreferences {

  var takeOffSpeed: Int
  fun takeOffSpeedFlow(): Flow<Int>


  var takeOffAltDiff: Int
  fun takeOffAltDiffFlow(): Flow<Int>

  var voiceGuidance: Boolean
  fun voiceGuidanceFlow(): Flow<Boolean>

  companion object {
    const val MIN_TAKE_OFF_SPEED = 5
    const val MAX_TAKE_OFF_SPEED = 25

    const val MIN_TAKE_OFF_ALT_DIFF = 2
    const val MAX_TAKE_OFF_ALT_DIFF = 15
  }
}