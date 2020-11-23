package com.apro.paraflight.di

import android.app.Application
import com.apro.core.navigation.AppRouter
import com.apro.core.navigation.di.NavigationModule
import com.apro.core.util.event.EventBus
import com.apro.core.voiceguidance.api.VoiceGuidance
import com.apro.core.voiceguidance.impl.VoiceGuidanceImpl

import com.apro.paraflight.ui.mapbox.MapboxInteractor
import com.apro.paraflight.ui.mapbox.MapboxInteractorImpl
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

  fun mapboxInteractor(): MapboxInteractor

  fun eventBus(): EventBus

  fun resources(): ResourceProvider

  fun appRouter(): AppRouter

  fun navigatorHolder(): NavigatorHolder

  fun voiceGuidance(): VoiceGuidance

  companion object {
    fun create(app: Application): AppComponent =
      DaggerAppComponent.builder()
        .appModule(AppModule(app))
        .build()
  }
}

@Module
class AppModule(private val app: Application) {

  @Provides
  @Singleton
  fun provideMapboxInteractor(): MapboxInteractor = MapboxInteractorImpl()

  @Provides
  @Singleton
  fun resourceProvider(): ResourceProvider = AndroidResourceProvider(app)

  @Provides
  @Singleton
  fun eventBusProvider(): EventBus = EventBus

  @Provides
  @Singleton
  fun provideVoiceGuidance(): VoiceGuidance = VoiceGuidanceImpl(app)
}



