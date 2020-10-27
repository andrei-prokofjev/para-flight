package com.apro.core.preferenes.impl

import android.app.Application
import android.content.Context
import com.apro.core.preferenes.api.ConstructionPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class ConstructionPreferencesImpl @Inject constructor(
  app: Application
) : ConstructionPreferences {

  private val prefs by lazy { app.getSharedPreferences(PREFS, Context.MODE_PRIVATE) }

  private val takeOffSpeedChannel = ConflatedBroadcastChannel<Int>()
  override fun takeOffSpeedFlow() = takeOffSpeedChannel.asFlow()
  override var takeOffSpeed: Int
    get() = prefs.getInt(TAKE_OFF_SPEED, DEFAULT_TAKE_OFF_SPEED)
    set(value) {
      prefs.edit().putInt(TAKE_OFF_SPEED, value).apply()
      GlobalScope.launch(Dispatchers.IO) { takeOffSpeedChannel.send(value) }
    }

  private val takeOffAltDiffChannel = ConflatedBroadcastChannel<Int>()
  override fun takeOffAltDiffFlow() = takeOffAltDiffChannel.asFlow()
  override var takeOffAltDiff: Int
    get() = prefs.getInt(TAKE_OFF_ALT_DIFF, DEFAULT_TAKE_OFF_ALT_DIFF)
    set(value) {
      prefs.edit().putInt(TAKE_OFF_ALT_DIFF, value).apply()
      GlobalScope.launch(Dispatchers.IO) { takeOffAltDiffChannel.send(value) }
    }

  private companion object {
    const val PREFS = "construction_preferences"

    const val DEFAULT_TAKE_OFF_SPEED = 20
    const val TAKE_OFF_SPEED = "take_off_speed"

    const val DEFAULT_TAKE_OFF_ALT_DIFF = 6
    const val TAKE_OFF_ALT_DIFF = "take_off_alt_diff"
  }
}