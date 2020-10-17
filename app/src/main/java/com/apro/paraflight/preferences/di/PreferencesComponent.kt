package com.apro.paraflight.preferences.di

import android.app.Application
import com.apro.paraflight.preferences.api.MapboxPreferences
import com.apro.paraflight.preferences.impl.MapboxPreferencesImpl
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

    fun initAndGet(app: Application): PreferencesApi {
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
}