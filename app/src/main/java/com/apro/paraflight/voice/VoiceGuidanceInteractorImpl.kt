package com.apro.paraflight.voice

import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.voiceguidance.api.VoiceGuidance
import javax.inject.Inject

class VoiceGuidanceInteractorImpl @Inject constructor(
  private val voiceGuidance: VoiceGuidance,
  val settingsPreferences: SettingsPreferences
) : VoiceGuidanceInteractor {
  override fun speak(text: String) {
    if (settingsPreferences.voiceGuidance) {
      voiceGuidance.speak(text)
    }
  }
}