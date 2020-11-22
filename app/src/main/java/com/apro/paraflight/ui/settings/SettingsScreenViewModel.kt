package com.apro.paraflight.ui.settings

import com.apro.core.navigation.AppRouter
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.ui.BaseViewModel
import com.apro.core.util.event.EventBus
import javax.inject.Inject

class SettingsScreenViewModel @Inject constructor(
  val appRouter: AppRouter,
  val eventBus: EventBus,
  private val settingsPreferences: SettingsPreferences
) : BaseViewModel() {


  fun saveVoiceGuidance(on: Boolean) {
    settingsPreferences.voiceGuidance = on
  }

  fun saveTakeOffSpeed(value: Int) {
    settingsPreferences.takeOffSpeed = value
  }

  fun saveTakeOffAlt(value: Int) {
    settingsPreferences.takeOffAltDiff = value
  }

  fun saveMinFlightSpeed(value: Int) {
    settingsPreferences.minFlightSpeed = value
  }

  fun saveUnits(value: SettingsPreferences.Units) {
    settingsPreferences.units = value
  }

  fun googleSignIn() {

  }

  fun onBackClicked() {
    appRouter.exit()
  }

  fun saveWindDetector(on: Boolean) {
    settingsPreferences.windDetector = on
  }

  fun saveWindDetectorPoints(value: Int) {
   settingsPreferences.windDetectionPoints = value
  }
}
