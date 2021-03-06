package com.apro.paraflight.ui.mapbox

import androidx.lifecycle.ViewModel
import com.apro.core.navigation.AppRouter
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.util.event.EventBus
import com.apro.core.voiceguidance.api.VoiceGuidance
import com.apro.paraflight.DI
import com.apro.paraflight.di.ViewModelFactory
import com.apro.paraflight.di.ViewModelKey
import com.apro.paraflight.ui.flight.FlightInteractor
import com.apro.paraflight.ui.flight.FlightInteractorImpl
import com.apro.paraflight.util.ResourceProvider
import com.apro.paraflight.voice.VoiceGuidanceInteractor
import com.apro.paraflight.voice.VoiceGuidanceInteractorImpl
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Component(modules = [MapboxScreenModule::class])
@Singleton
interface MapboxScreenComponent {

  fun viewModelFactory(): ViewModelFactory

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun appRouter(appRouter: AppRouter): Builder

    @BindsInstance
    fun resources(resourceProvider: ResourceProvider): Builder

    @BindsInstance
    fun mapboxPreferences(mapboxPreferences: MapboxPreferences): Builder

    @BindsInstance
    fun eventBus(eventBus: EventBus): Builder

    @BindsInstance
    fun mapboxInteractor(mapboxInteractor: MapboxInteractor): Builder

    @BindsInstance
    fun settingsPreferences(settingsPref: SettingsPreferences): Builder

    @BindsInstance
    fun voiceGuidance(voiceGuidance: VoiceGuidance): Builder

    fun build(): MapboxScreenComponent
  }

  companion object {
    fun create() = with(DI.appComponent) {
      DaggerMapboxScreenComponent.builder()
        .appRouter(appRouter())
        .resources(resources())
        .mapboxPreferences(mapboxPreferences())
        .eventBus(eventBus())
        .mapboxInteractor(mapboxInteractor())
        .settingsPreferences(settingsPreferences())
        .voiceGuidance(voiceGuidance())
        .build()
    }
  }
}


@Module
abstract class MapboxScreenModule {

  @Binds
  @IntoMap
  @ViewModelKey(MapboxViewModel::class)
  abstract fun mapboxViewModel(viewModel: MapboxViewModel): ViewModel

  @Binds
  abstract fun voiceGuidanceInteractor(interactor: VoiceGuidanceInteractorImpl): VoiceGuidanceInteractor

  @Binds
  abstract fun flightInteractor(interactor: FlightInteractorImpl): FlightInteractor

}
