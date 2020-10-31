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

  private val _minFlightSpeedData = MutableLiveData(settingsPreferences.minFlightSpeed)
  val minFlightSpeedData: LiveData<Int> = _minFlightSpeedData


  private val _voiceGuidanceData = MutableLiveData(settingsPreferences.voiceGuidance)
  val voiceGuidanceData: LiveData<Boolean> = _voiceGuidanceData

  private val _unitsData = MutableLiveData(settingsPreferences.units)
  val unitsData: LiveData<SettingsPreferences.Units> = _unitsData

  init {
    _takeOffSpeedData.postValue(settingsPreferences.takeOffSpeed)
    _takeOffAltDiffData.postValue(settingsPreferences.takeOffAltDiff)
    _minFlightSpeedData.postValue(settingsPreferences.minFlightSpeed)
    _voiceGuidanceData.postValue(settingsPreferences.voiceGuidance)
    _unitsData.postValue(settingsPreferences.units)
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

  fun saveMinFlightSpeed(value: Int) {
    settingsPreferences.minFlightSpeed = value
  }

  fun saveUnits(value: SettingsPreferences.Units) {
    settingsPreferences.units = value
  }

  fun onBackClicked() {
    appRouter.exit()
  }


}
