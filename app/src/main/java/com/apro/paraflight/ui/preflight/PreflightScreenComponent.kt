package com.apro.paraflight.ui.preflight

import androidx.lifecycle.ViewModel
import com.apro.core.navigation.AppRouter
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core.util.event.EventBus
import com.apro.paraflight.DI
import com.apro.paraflight.di.ViewModelFactory
import com.apro.paraflight.di.ViewModelKey
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [PreflightScreenModule::class])
interface PreflightScreenComponent {

  fun viewModelFactory(): ViewModelFactory

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun appRouter(router: AppRouter): Builder

    @BindsInstance
    fun eventBus(eventBus: EventBus): Builder

    @BindsInstance
    fun mapboxPreferences(mapboxPreferences: MapboxPreferences): Builder

    fun build(): PreflightScreenComponent
  }

  companion object {
    fun create() = with(DI.appComponent) {
      DaggerPreflightScreenComponent.builder()
        .appRouter(appRouter())
        .eventBus(eventBus())
        .mapboxPreferences(mapboxPreferences())
        .build()
    }
  }
}

@Module
abstract class PreflightScreenModule {
  @Binds
  @IntoMap
  @ViewModelKey(PreflightScreenViewModel::class)
  abstract fun preflightScreenViewModel(viewModel: PreflightScreenViewModel): ViewModel
}