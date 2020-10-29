package com.apro.paraflight.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

  private val _takeOffSpeedData = MutableLiveData(settingsPreferences.takeOffSpeed)
  val takeOffSpeedData: LiveData<Int> = _takeOffSpeedData

  private val _takeOffAltDiffData = MutableLiveData(settingsPreferences.takeOffAltDiff)
  val takeOffAltDiffData: LiveData<Int> = _takeOffAltDiffData

  private val _voiceGuidanceData = MutableLiveData(settingsPreferences.voiceGuidance)
  val voiceGuidanceData: LiveData<Boolean> = _voiceGuidanceData

  init {
    _voiceGuidanceData.postValue(settingsPreferences.voiceGuidance)
    _takeOffSpeedData.postValue(settingsPreferences.takeOffSpeed)
    _takeOffAltDiffData.postValue(settingsPreferences.takeOffAltDiff)
  }

  fun saveVoiceGuidance(on: Boolean) {
    settingsPreferences.voiceGuidance = on
  }

  fun saveTakeOffSpeed(value: Int) {
    settingsPreferences.takeOffSpeed = value
  }

  fun saveTakeOffAlt(value: Int) {
    settingsPreferences.takeOffAltDiff = value
  }

  fun onBackClicked() {
    appRouter.exit()
  }
}
