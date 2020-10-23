package com.apro.paraflight.viewmodel

import androidx.lifecycle.ViewModel
import com.apro.core.db.api.data.store.RouteStore
import com.apro.core.db.api.di.DatabaseApi
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.paraflight.DI
import com.apro.paraflight.di.ScreenScope
import com.apro.paraflight.di.ViewModelFactory
import com.apro.paraflight.di.ViewModelKey
import com.apro.paraflight.util.ResourceProvider
import com.github.terrakok.cicerone.Router
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [FlightScreenModule::class])
@ScreenScope
interface FlightScreenComponent {

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
    fun appRouter(appRouter: Router): Builder

    fun build(): FlightScreenComponent
  }

  companion object {
    fun create() = with(DI.appComponent) {
      DaggerFlightScreenComponent.builder()
        .resources(resources())
        .mapboxPreferences(DI.preferencesApi.mapbox())
        .flightsStore(DI.databaseApi.flightsStore())
        .databaseApi(DI.databaseApi)
        .appRouter(DI.appComponent.appRouter())
        .build()
    }
  }
}

@Module
abstract class FlightScreenModule {

  @Binds
  @IntoMap
  @ViewModelKey(FlightViewModel::class)
  abstract fun mainScreenViewModel(viewModel: FlightViewModel): ViewModel
}