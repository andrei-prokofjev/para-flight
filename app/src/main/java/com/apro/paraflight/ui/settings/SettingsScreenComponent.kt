package com.apro.paraflight.ui.settings

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

@Component(modules = [SettingsScreenModule::class])
interface SettingsScreenComponent {

  fun viewModelFactory(): ViewModelFactory

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun appRouter(router: Router): Builder

    @BindsInstance
    fun eventBus(eventBus: EventBus): Builder

    fun build(): SettingsScreenComponent
  }

  companion object {
    fun create() = with(DI.appComponent) {
      DaggerSettingsScreenComponent.builder()
        .appRouter(appRouter())
        .eventBus(eventBus())
        .build()
    }
  }
}

@Module
abstract class SettingsScreenModule {
  @Binds
  @IntoMap
  @ViewModelKey(SettingsScreenViewModel::class)
  abstract fun settingsScreenViewModel(viewModel: SettingsScreenViewModel): ViewModel
}