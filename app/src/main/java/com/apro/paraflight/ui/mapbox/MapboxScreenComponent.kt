package com.apro.paraflight.ui.mapbox

import androidx.lifecycle.ViewModel
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.util.event.EventBus
import com.apro.paraflight.DI
import com.apro.paraflight.di.ViewModelFactory
import com.apro.paraflight.di.ViewModelKey
import com.apro.paraflight.mapbox.FlightRepository
import com.apro.paraflight.ui.flight.FlightInteractor
import com.apro.paraflight.ui.flight.FlightInteractorImpl
import com.apro.paraflight.util.ResourceProvider
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
    fun resources(resourceProvider: ResourceProvider): Builder

    @BindsInstance
    fun mapboxPreferences(mapboxPreferences: MapboxPreferences): Builder

    @BindsInstance
    fun eventBus(eventBus: EventBus): Builder

    @BindsInstance
    fun flightRepository(repository: FlightRepository): Builder

    @BindsInstance
    fun settingsPreferences(settingsPref: SettingsPreferences): Builder

    fun build(): MapboxScreenComponent
  }

  companion object {
    fun create() = with(DI.appComponent) {
      DaggerMapboxScreenComponent.builder()
        .resources(resources())
        .mapboxPreferences(DI.preferencesApi.mapbox())
        .eventBus(eventBus())
        .flightRepository(flightRepository())
        .settingsPreferences(DI.preferencesApi.settings())
        .build()
    }
  }
}

@Module
abstract class MapboxScreenModule {

  @Binds
  @IntoMap
  @ViewModelKey(MapboxViewModel::class)
  abstract fun mainScreenViewModel(viewModel: MapboxViewModel): ViewModel

  @Binds
  abstract fun flightInteractor(interactor: FlightInteractorImpl): FlightInteractor
}