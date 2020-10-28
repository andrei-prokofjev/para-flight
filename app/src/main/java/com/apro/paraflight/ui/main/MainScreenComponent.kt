package com.apro.paraflight.ui.main

import androidx.lifecycle.ViewModel
import com.apro.core.navigation.AppRouter
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core.util.event.EventBus
import com.apro.core.voiceguidance.api.VoiceGuidance
import com.apro.paraflight.DI
import com.apro.paraflight.di.ViewModelFactory
import com.apro.paraflight.di.ViewModelKey
import com.apro.paraflight.mapbox.FlightRepository
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [MainScreenModule::class])
interface MainScreenComponent {

  fun viewModelFactory(): ViewModelFactory

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun appRouter(router: AppRouter): Builder

    @BindsInstance
    fun eventBus(eventBus: EventBus): Builder

    @BindsInstance
    fun mapboxPreferences(mapboxPreferences: MapboxPreferences): Builder

    @BindsInstance
    fun flightLocationEngine(repository: FlightRepository): Builder

    @BindsInstance
    fun voiceGuidance(voiceGuidance: VoiceGuidance): Builder

    fun build(): MainScreenComponent
  }

  companion object {
    fun create() = with(DI.appComponent) {
      DaggerMainScreenComponent.builder()
        .appRouter(appRouter())
        .eventBus(eventBus())
        .mapboxPreferences(DI.preferencesApi.mapbox())
        .flightLocationEngine(flightRepository())
        .voiceGuidance(voiceGuidance())
        .build()
    }
  }
}

@Module
abstract class MainScreenModule {
  @Binds
  @IntoMap
  @ViewModelKey(MainScreenViewModel::class)
  abstract fun mainScreenViewModel(viewModel: MainScreenViewModel): ViewModel
}