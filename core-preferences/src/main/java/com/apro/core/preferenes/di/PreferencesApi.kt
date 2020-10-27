package com.apro.core.preferenes.di

import com.apro.core.preferenes.api.ConstructionPreferences
import com.apro.core.preferenes.api.MapboxPreferences


interface PreferencesApi {
  fun mapbox(): MapboxPreferences

  fun construction(): ConstructionPreferences

}