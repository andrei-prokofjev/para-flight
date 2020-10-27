package com.apro.paraflight.ui.construction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.navigation.AppRouter
import com.apro.core.preferenes.api.ConstructionPreferences
import com.apro.core.ui.BaseViewModel
import com.apro.core.util.event.EventBus
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConstructionScreenViewModel @Inject constructor(
  val appRouter: AppRouter,
  val eventBus: EventBus,
  private val constructionPreferences: ConstructionPreferences
) : BaseViewModel() {

  private val _takeOffSpeedData = MutableLiveData(constructionPreferences.takeOffSpeed)
  val takeOffSpeedData: LiveData<Int> = _takeOffSpeedData

  private val _takeOffAltDiffData = MutableLiveData(constructionPreferences.takeOffAltDiff)
  val takeOffAltDiffData: LiveData<Int> = _takeOffAltDiffData

  private val _voiceGuidanceData = MutableLiveData(constructionPreferences.voiceGuidance)
  val voiceGuidanceData: LiveData<Boolean> = _voiceGuidanceData

  init {
    viewModelScope.launch {
      constructionPreferences.voiceGuidanceFlow().collect {
        _voiceGuidanceData.postValue(it)
      }
    }

    viewModelScope.launch {
      constructionPreferences.takeOffSpeedFlow().collect {
        _takeOffSpeedData.postValue(it)
      }
    }
  }

  fun saveTakeOffDetectionParams(takeOffSpeed: Int, takeOffAltDiff: Int) {
    constructionPreferences.takeOffSpeed = takeOffSpeed
    constructionPreferences.takeOffAltDiff = takeOffAltDiff
  }

  fun saveVoiceGuidance(on: Boolean) {
    constructionPreferences.voiceGuidance = on
  }

  fun onBackClicked() {
    appRouter.exit()
  }
}
