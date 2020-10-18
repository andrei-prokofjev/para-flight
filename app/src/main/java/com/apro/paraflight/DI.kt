package com.apro.paraflight

import com.apro.core.db.api.di.DatabaseApi
import com.apro.core.network.di.NetworkComponent
import com.apro.core.preferenes.di.PreferencesApi
import com.apro.paraflight.core.di.AppComponent


object DI {
  lateinit var appComponent: AppComponent
    internal set

  lateinit var networkComponent: NetworkComponent

  lateinit var preferencesApi: PreferencesApi

  lateinit var databaseApi: DatabaseApi
}