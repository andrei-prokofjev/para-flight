package com.apro.paraflight.ui.logbook

import androidx.lifecycle.ViewModel
import com.apro.core.navigation.AppRouter
import com.apro.paraflight.DI
import com.apro.paraflight.di.ViewModelFactory
import com.apro.paraflight.di.ViewModelKey
import com.apro.paraflight.ui.mapbox.MapboxInteractor
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [LogbookScreenModule::class])
interface LogbookScreenComponent {

  fun viewModelFactory(): ViewModelFactory

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun mapboxInteractor(mapboxInteractor: MapboxInteractor): Builder

    @BindsInstance
    fun appRouter(appRouter: AppRouter): Builder

    fun build(): LogbookScreenComponent
  }

  companion object {
    fun create() = with(DI.appComponent) {
      DaggerLogbookScreenComponent.builder()
        .appRouter(appRouter())
        .mapboxInteractor(mapboxInteractor())
        .build()
    }
  }
}

@Module
abstract class LogbookScreenModule {

  @Binds
  @IntoMap
  @ViewModelKey(LogbookViewModel::class)
  abstract fun logbookViewModel(viewModel: LogbookViewModel): ViewModel

  @Binds
  abstract fun logbookInteractor(interactor: LogbookInteractorImpl): LogbookInteractor

  @Binds
  abstract fun logbookRepository(interactor: LogbookRepositoryImpl): LogbookRepository
}

