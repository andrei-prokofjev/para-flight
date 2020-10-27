package com.apro.paraflight.ui.settings

import androidx.lifecycle.ViewModel
import com.apro.core.navigation.AppRouter
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.util.event.EventBus
import com.apro.paraflight.DI
import com.apro.paraflight.di.ViewModelFactory
import com.apro.paraflight.di.ViewModelKey
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
    fun appRouter(router: AppRouter): Builder

    @BindsInstance
    fun eventBus(eventBus: EventBus): Builder

    @BindsInstance
    fun settingsPrefs(settingsPreferences: SettingsPreferences): Builder

    fun build(): SettingsScreenComponent
  }

  companion object {
    fun create() = with(DI.appComponent) {
      DaggerSettingsScreenComponent.builder()
        .appRouter(appRouter())
        .eventBus(eventBus())
        .settingsPrefs(DI.preferencesApi.construction())
        .build()
    }
  }
}

@Module
abstract class SettingsScreenModule {
  @Binds
  @IntoMap
  @ViewModelKey(SettingsScreenViewModel::class)
  abstract fun constructionScreenViewModel(viewModel: SettingsScreenViewModel): ViewModel
}