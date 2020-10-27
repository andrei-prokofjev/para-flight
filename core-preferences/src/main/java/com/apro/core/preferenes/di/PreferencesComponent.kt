package com.apro.core.preferenes.di

import android.app.Application
import com.apro.core.preferenes.api.ConstructionPreferences
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core.preferenes.impl.ConstructionPreferencesImpl
import com.apro.core.preferenes.impl.MapboxPreferencesImpl
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Component(modules = [PreferencesModule::class])
@Singleton
interface PreferencesComponent : PreferencesApi {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun app(app: Application): Builder

    fun build(): PreferencesComponent
  }

  companion object {
    private var preferencesComponent: PreferencesComponent? = null

    fun create(app: Application): PreferencesApi {
      if (preferencesComponent == null) {
        preferencesComponent = DaggerPreferencesComponent.builder()
          .app(app)
          .build()
      }
      return requireNotNull(preferencesComponent)
    }

    fun get(): PreferencesApi = requireNotNull(preferencesComponent)
  }
}

@Module
abstract class PreferencesModule {
  @Binds
  @Singleton
  internal abstract fun bindsMapboxPrefs(mapboxPreferences: MapboxPreferencesImpl): MapboxPreferences

  @Binds
  @Singleton
  internal abstract fun bindsConstructionPrefs(constructionPreferences: ConstructionPreferencesImpl): ConstructionPreferences
}