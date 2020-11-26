package com.apro.core.preferenes.di

import android.app.Application
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.preferenes.api.UserProfilePreferences
import com.apro.core.preferenes.impl.MapboxPreferencesImpl
import com.apro.core.preferenes.impl.SettingsPreferencesImpl
import com.apro.core.preferenes.impl.UserProfilePreferencesImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PreferencesModule(private val app: Application) {

  @Provides
  @Singleton
  fun provideMapboxPreferences(): MapboxPreferences = MapboxPreferencesImpl(app)

  @Provides
  @Singleton
  fun provideSettingsPreferences(): SettingsPreferences = SettingsPreferencesImpl(app)

  @Provides
  @Singleton
  fun provideUserProfilePreferences(): UserProfilePreferences = UserProfilePreferencesImpl(app)

}