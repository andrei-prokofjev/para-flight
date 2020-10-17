package com.apro.paraflight

import com.apro.core.network.di.NetworkComponent
import com.apro.paraflight.di.AppComponent
import com.apro.paraflight.preferences.di.PreferencesApi


object DI {
  lateinit var appComponent: AppComponent
    internal set

  lateinit var networkComponent: NetworkComponent
    internal set

  lateinit var preferencesApi: PreferencesApi
    internal set
}