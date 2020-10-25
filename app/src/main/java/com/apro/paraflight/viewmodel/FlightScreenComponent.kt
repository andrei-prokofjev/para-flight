package com.apro.paraflight.viewmodel

import androidx.lifecycle.ViewModel
import com.apro.core.util.event.EventBus
import com.apro.paraflight.DI
import com.apro.paraflight.di.ViewModelFactory
import com.apro.paraflight.di.ViewModelKey
import com.apro.paraflight.mapbox.FlightLocationEngine
import com.github.terrakok.cicerone.Router
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
    fun appRouter(router: Router): Builder

    @BindsInstance
    fun eventBus(eventBus: EventBus): Builder

    @BindsInstance
    fun flightLocationEngine(flightLocationEngine: FlightLocationEngine): Builder

    fun build(): FlightScreenComponent
  }

  companion object {
    fun create() = with(DI.appComponent) {
      DaggerFlightScreenComponent.builder()
        .appRouter(appRouter())
        .eventBus(eventBus())
        .flightLocationEngine(flightLocationEngine())
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
}