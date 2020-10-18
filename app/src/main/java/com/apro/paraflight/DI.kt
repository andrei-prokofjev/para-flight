package com.apro.paraflight

import com.apro.core.network.di.NetworkComponent
import com.apro.core.preferenes.di.PreferencesApi
import com.apro.paraflight.core.di.AppComponent


object DI {
  lateinit var appComponent: AppComponent
    internal set

  lateinit var networkComponent: NetworkComponent
    internal set

  lateinit var preferencesApi: PreferencesApi
    internal set
}