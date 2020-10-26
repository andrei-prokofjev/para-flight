package com.apro.paraflight.ui.profile

import androidx.lifecycle.ViewModel
import com.apro.core.util.event.EventBus
import com.apro.paraflight.DI
import com.apro.paraflight.di.ViewModelFactory
import com.apro.paraflight.di.ViewModelKey

import com.github.terrakok.cicerone.Router
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [ProfileScreenModule::class])
interface ProfileScreenComponent {

  fun viewModelFactory(): ViewModelFactory

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun appRouter(router: Router): Builder

    @BindsInstance
    fun eventBus(eventBus: EventBus): Builder

    fun build(): ProfileScreenComponent
  }

  companion object {
    fun create() = with(DI.appComponent) {
      DaggerProfileScreenComponent.builder()
        .appRouter(appRouter())
        .eventBus(eventBus())
        .build()
    }
  }
}

@Module
abstract class ProfileScreenModule {
  @Binds
  @IntoMap
  @ViewModelKey(ProfileScreenViewModel::class)
  abstract fun flightScreenViewModel(viewModel: ProfileScreenViewModel): ViewModel
}