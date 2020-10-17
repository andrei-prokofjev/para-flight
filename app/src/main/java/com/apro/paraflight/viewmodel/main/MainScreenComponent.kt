package com.apro.paraflight.viewmodel.main

import androidx.lifecycle.ViewModel
import com.apro.paraflight.DI
import com.apro.paraflight.di.ScreenScope
import com.apro.paraflight.di.ViewModelFactory
import com.apro.paraflight.di.ViewModelKey
import com.apro.paraflight.preferences.api.MapboxPreferences
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


    fun build(): MainScreenComponent
  }

  companion object {
    fun create() = with(DI.appComponent) {
      DaggerMainScreenComponent.builder()
        .resources(resources())
        .mapboxPreferences(DI.preferencesApi.mapbox())
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