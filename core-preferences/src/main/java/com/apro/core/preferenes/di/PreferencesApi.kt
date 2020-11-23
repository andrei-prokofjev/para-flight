package com.apro.core.preferenes.di

import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.preferenes.api.UserProfilePreferences


interface PreferencesApi {

  fun mapbox(): MapboxPreferences

  fun settings(): SettingsPreferences

  fun userProfile(): UserProfilePreferences

}