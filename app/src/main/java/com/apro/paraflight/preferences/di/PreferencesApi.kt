package com.apro.paraflight.preferences.di

import com.apro.paraflight.preferences.api.MapboxPreferences


interface PreferencesApi {
  fun mapbox(): MapboxPreferences

}