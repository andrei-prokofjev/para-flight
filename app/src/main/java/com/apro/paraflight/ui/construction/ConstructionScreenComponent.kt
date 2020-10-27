package com.apro.paraflight.ui.construction

import androidx.lifecycle.ViewModel
import com.apro.core.navigation.AppRouter
import com.apro.core.preferenes.api.ConstructionPreferences
import com.apro.core.util.event.EventBus
import com.apro.paraflight.DI
import com.apro.paraflight.di.ViewModelFactory
import com.apro.paraflight.di.ViewModelKey
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [ConstructionScreenModule::class])
interface ConstructionScreenComponent {

  fun viewModelFactory(): ViewModelFactory

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun appRouter(router: AppRouter): Builder

    @BindsInstance
    fun eventBus(eventBus: EventBus): Builder

    @BindsInstance
    fun constructionPrefs(constructionPreferences: ConstructionPreferences): Builder

    fun build(): ConstructionScreenComponent
  }

  companion object {
    fun create() = with(DI.appComponent) {
      DaggerConstructionScreenComponent.builder()
        .appRouter(appRouter())
        .eventBus(eventBus())
        .constructionPrefs(DI.preferencesApi.construction())
        .build()
    }
  }
}

@Module
abstract class ConstructionScreenModule {
  @Binds
  @IntoMap
  @ViewModelKey(ConstructionScreenViewModel::class)
  abstract fun constructionScreenViewModel(viewModel: ConstructionScreenViewModel): ViewModel
}