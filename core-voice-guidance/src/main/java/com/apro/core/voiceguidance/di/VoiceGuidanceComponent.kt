package com.apro.core.voiceguidance.di

import android.app.Application
import com.apro.core.voiceguidance.api.VoiceGuidance
import com.apro.core.voiceguidance.impl.VoiceGuidanceImpl
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Component(modules = [VoiceGuidanceModule::class])
@Singleton
interface VoiceGuidanceComponent {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun app(app: Application): Builder

    fun build(): VoiceGuidanceComponent
  }

  companion object {
    fun create(app: Application) = DaggerVoiceGuidanceComponent.builder()
      .app(app)
      .build()
  }
}

@Module
abstract class VoiceGuidanceModule {

  @Binds
  @Singleton
  internal abstract fun bindsVoiceGuidance(voiceGuidance: VoiceGuidanceImpl): VoiceGuidance
}