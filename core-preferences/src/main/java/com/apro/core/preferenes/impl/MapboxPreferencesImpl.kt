package com.apro.core.preferenes.impl

import android.app.Application
import android.content.Context
import com.apro.core.preferenes.api.MapboxPreferences
import javax.inject.Inject


class MapboxPreferencesImpl @Inject constructor(
  app: Application
) : MapboxPreferences {

  private val prefs by lazy { app.getSharedPreferences(PREFS, Context.MODE_PRIVATE) }

  override var mapStyle: MapboxPreferences.MapStyle
    get() {
      val value = prefs.getInt(MAP_STYLE, MapboxPreferences.MapStyle.MAPBOX_STREETS.ordinal)
      return MapboxPreferences.MapStyle.values()[value]
    }
    set(value) {
      prefs.edit().putInt(MAP_STYLE, value.ordinal).apply()
    }


  private companion object {
    const val PREFS = "map_preferences"
    const val MAP_STYLE = "map_style"
  }
}