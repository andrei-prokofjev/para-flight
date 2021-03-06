package com.apro.core.voiceguidance.impl

import android.content.Context
import android.speech.tts.TextToSpeech
import com.apro.core.voiceguidance.api.VoiceGuidance
import timber.log.Timber
import javax.inject.Inject

class VoiceGuidanceImpl @Inject constructor(
  context: Context
) : VoiceGuidance {

  private val textToSpeech = TextToSpeech(context, { }, null)

  override fun speak(text: String) {
    Timber.d(">>> speak: $text")
    textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, null)
  }


}