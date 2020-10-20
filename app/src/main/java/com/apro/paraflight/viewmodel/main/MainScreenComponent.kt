package com.apro.paraflight.viewmodel.main

import androidx.lifecycle.ViewModel
import com.apro.core.db.api.data.store.RouteStore
import com.apro.core.db.api.di.DatabaseApi
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.paraflight.DI
import com.apro.paraflight.core.di.ScreenScope
import com.apro.paraflight.core.di.ViewModelFactory
import com.apro.paraflight.core.di.ViewModelKey
import com.apro.paraflight.util.ResourceProvider
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [MainScreenModule::class])
@ScreenScope
interface MainScreenComponent {

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

    fun build(): MainScreenComponent
  }

  companion object {
    fun create() = with(DI.appComponent) {
      DaggerMainScreenComponent.builder()
        .resources(resources())
        .mapboxPreferences(DI.preferencesApi.mapbox())
        .flightsStore(DI.databaseApi.flightsStore())
        .databaseApi(DI.databaseApi)
        .build()
    }
  }
}

@Module
abstract class MainScreenModule {

  @Binds
  @IntoMap
  @ViewModelKey(MapboxViewModel::class)
  abstract fun mainScreenViewModel(viewModel: MapboxViewModel): ViewModel
}