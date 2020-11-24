package com.apro.paraflight.ui.about

import androidx.lifecycle.ViewModel
import com.apro.core.navigation.AppRouter
import com.apro.core.util.event.EventBus
import com.apro.paraflight.DI
import com.apro.paraflight.di.ViewModelFactory
import com.apro.paraflight.di.ViewModelKey
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [AboutScreenModule::class])
interface AboutScreenComponent {

  fun viewModelFactory(): ViewModelFactory

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun appRouter(router: AppRouter): Builder

    @BindsInstance
    fun eventBus(eventBus: EventBus): Builder

    fun build(): AboutScreenComponent
  }

  companion object {
    fun create() = with(DI.appComponent) {
      DaggerAboutScreenComponent.builder()
        .appRouter(appRouter())
        .eventBus(eventBus())
        .build()
    }
  }
}

@Module
abstract class AboutScreenModule {
  @Binds
  @IntoMap
  @ViewModelKey(AboutScreenViewModel::class)
  abstract fun flightScreenViewModel(viewModel: AboutScreenViewModel): ViewModel
}