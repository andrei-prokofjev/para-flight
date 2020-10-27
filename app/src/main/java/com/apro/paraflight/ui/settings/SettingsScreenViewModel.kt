package com.apro.paraflight.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.navigation.AppRouter
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.ui.BaseViewModel
import com.apro.core.util.event.EventBus
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
    viewModelScope.launch {
      settingsPreferences.voiceGuidanceFlow().collect {
        _voiceGuidanceData.postValue(it)
      }
    }

    viewModelScope.launch {
      settingsPreferences.takeOffSpeedFlow().collect {
        _takeOffSpeedData.postValue(it)
      }
    }
  }

  fun saveTakeOffDetectionParams(takeOffSpeed: Int, takeOffAltDiff: Int) {
    settingsPreferences.takeOffSpeed = takeOffSpeed
    settingsPreferences.takeOffAltDiff = takeOffAltDiff
  }

  fun saveVoiceGuidance(on: Boolean) {
    settingsPreferences.voiceGuidance = on
  }

  fun onBackClicked() {
    appRouter.exit()
  }
}
