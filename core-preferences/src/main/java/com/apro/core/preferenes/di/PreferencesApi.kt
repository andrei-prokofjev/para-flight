package com.apro.core.preferenes.di

import com.apro.core.preferenes.api.MapboxPreferences


interface PreferencesApi {
  fun mapbox(): MapboxPreferences

}