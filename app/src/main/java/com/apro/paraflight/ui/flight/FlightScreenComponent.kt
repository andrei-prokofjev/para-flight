package com.apro.paraflight.ui.flight

import androidx.lifecycle.ViewModel
import com.apro.core.navigation.AppRouter
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.util.event.EventBus
import com.apro.core.voiceguidance.api.VoiceGuidance
import com.apro.paraflight.DI
import com.apro.paraflight.di.ViewModelFactory
import com.apro.paraflight.di.ViewModelKey
import com.apro.paraflight.interactors.VoiceGuidanceInteractor
import com.apro.paraflight.interactors.VoiceGuidanceInteractorImpl
import com.apro.paraflight.mapbox.MapboxLocationEngineRepository
import com.apro.paraflight.util.ResourceProvider
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [FlightScreenModule::class])
interface FlightScreenComponent {

  fun viewModelFactory(): ViewModelFactory

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun resources(resourceProvider: ResourceProvider): Builder

    @BindsInstance
    fun appRouter(router: AppRouter): Builder

    @BindsInstance
    fun eventBus(eventBus: EventBus): Builder

    @BindsInstance
    fun flightRepository(repository: MapboxLocationEngineRepository): Builder

    @BindsInstance
    fun settingsPreferences(preferences: SettingsPreferences): Builder

    @BindsInstance
    fun voiceGuidance(voiceGuidance: VoiceGuidance): Builder

    fun build(): FlightScreenComponent
  }

  companion object {
    fun create() = with(DI.appComponent) {
      DaggerFlightScreenComponent.builder()
        .resources(resources())
        .appRouter(appRouter())
        .eventBus(eventBus())
        .flightRepository(flightRepository())
        .voiceGuidance(voiceGuidance())
        .settingsPreferences(DI.preferencesApi.settings())
        .build()
    }
  }
}

@Module
abstract class FlightScreenModule {
  @Binds
  @IntoMap
  @ViewModelKey(FlightScreenViewModel::class)
  abstract fun flightScreenViewModel(viewModel: FlightScreenViewModel): ViewModel

  @Binds
  abstract fun flightInteractor(interactor: FlightInteractorImpl): FlightInteractor

  @Binds
  abstract fun voiceGuidanceInteractor(interactor: VoiceGuidanceInteractorImpl): VoiceGuidanceInteractor
}