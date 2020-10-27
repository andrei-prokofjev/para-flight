package com.apro.paraflight.ui.mapbox

import androidx.lifecycle.ViewModel
import com.apro.core.db.api.data.store.RouteStore
import com.apro.core.db.api.di.DatabaseApi
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core.util.event.EventBus
import com.apro.paraflight.DI
import com.apro.paraflight.di.ViewModelFactory
import com.apro.paraflight.di.ViewModelKey
import com.apro.paraflight.mapbox.FlightLocationRepositoryImpl
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
    fun flightsStore(routeStore: RouteStore): Builder

    @BindsInstance
    fun databaseApi(databaseApi: DatabaseApi): Builder

    @BindsInstance
    fun eventBus(eventBus: EventBus): Builder

    @BindsInstance
    fun flightLocationEngine(locationEngineImpl: FlightLocationRepositoryImpl): Builder

    fun build(): MapboxScreenComponent
  }

  companion object {
    fun create() = with(DI.appComponent) {
      DaggerMapboxScreenComponent.builder()
        .resources(resources())
        .mapboxPreferences(DI.preferencesApi.mapbox())
        .flightsStore(DI.databaseApi.routeStore())
        .databaseApi(DI.databaseApi)
        .eventBus(eventBus())
        .flightLocationEngine(flightLocationEngine())
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
}