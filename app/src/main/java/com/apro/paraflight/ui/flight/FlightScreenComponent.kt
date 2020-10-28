package com.apro.paraflight.ui.flight

import androidx.lifecycle.ViewModel
import com.apro.core.navigation.AppRouter
import com.apro.core.util.event.EventBus
import com.apro.paraflight.DI
import com.apro.paraflight.di.ViewModelFactory
import com.apro.paraflight.di.ViewModelKey
import com.apro.paraflight.mapbox.FlightRepository
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
    fun appRouter(router: AppRouter): Builder

    @BindsInstance
    fun eventBus(eventBus: EventBus): Builder

    @BindsInstance
    fun flightRepository(repository: FlightRepository): Builder

    fun build(): FlightScreenComponent
  }

  companion object {
    fun create() = with(DI.appComponent) {
      DaggerFlightScreenComponent.builder()
        .appRouter(appRouter())
        .eventBus(eventBus())
        .flightRepository(flightRepository())
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
}