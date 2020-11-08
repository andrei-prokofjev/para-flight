package com.apro.paraflight.ui.flight

import androidx.lifecycle.ViewModel
import com.apro.core.location.engine.api.LocationEngine
import com.apro.core.navigation.AppRouter
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.util.event.EventBus
import com.apro.core.voiceguidance.api.VoiceGuidance
import com.apro.paraflight.DI
import com.apro.paraflight.di.ViewModelFactory
import com.apro.paraflight.di.ViewModelKey
import com.apro.paraflight.ui.mapbox.MapboxInteractor
import com.apro.paraflight.util.ResourceProvider
import com.apro.paraflight.voice.VoiceGuidanceInteractor
import com.apro.paraflight.voice.VoiceGuidanceInteractorImpl
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
    fun mapboxInteractor(mapboxInteractor: MapboxInteractor): Builder

    @BindsInstance
    fun locationEngine(locationEngine: LocationEngine): Builder

    @BindsInstance
    fun mapboxPreferences(mapboxPreferences: MapboxPreferences): Builder

    @BindsInstance
    fun settingsPreferences(preferences: SettingsPreferences): Builder

    @BindsInstance
    fun voiceGuidance(voiceGuidance: VoiceGuidance): Builder

    fun build(): FlightScreenComponent
  }

  companion object {
    fun create(engine: LocationEngine) = with(DI.appComponent) {
      DaggerFlightScreenComponent.builder()
        .resources(resources())
        .mapboxPreferences(DI.preferencesApi.mapbox())
        .appRouter(appRouter())
        .eventBus(eventBus())
        .mapboxInteractor(mapboxInteractor())
        .locationEngine(engine)
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