package com.apro.paraflight.di

import android.content.Context
import com.apro.core.navigation.AppRouter
import com.apro.core.navigation.di.NavigationModule
import com.apro.core.util.event.EventBus
import com.apro.core.voiceguidance.impl.VoiceGuidanceImpl
import com.apro.paraflight.util.AndroidResourceProvider
import com.apro.paraflight.util.ResourceProvider
import com.github.terrakok.cicerone.NavigatorHolder
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Component(modules = [AppModule::class, NavigationModule::class])
@Singleton
interface AppComponent {

  fun eventBus(): EventBus

  fun resources(): ResourceProvider

  fun appRouter(): AppRouter

  fun navigatorHolder(): NavigatorHolder

  fun voiceGuidance(): VoiceGuidanceImpl

  companion object {
    fun create(context: Context): AppComponent =
      DaggerAppComponent.builder()
        .appModule(AppModule(context))
        .build()
  }
}

@Module
class AppModule(private val context: Context) {
  @Provides
  @Singleton
  fun resourceProvider(): ResourceProvider = AndroidResourceProvider(context)

  @Provides
  @Singleton
  fun eventBusProvider(): EventBus = EventBus

  @Provides
  @Singleton
  fun provideVoiceGuidance(): VoiceGuidanceImpl = VoiceGuidanceImpl(context)
}



