package com.apro.paraflight

import android.app.Application
import com.apro.core.db.impl.di.DatabaseComponent
import com.apro.core.network.di.DaggerNetworkComponent
import com.apro.core.preferenes.di.PreferencesComponent
import com.apro.paraflight.core.di.DaggerAppComponent
import timber.log.Timber

@Suppress("unused")
class App : Application() {


  override fun onCreate() {
    super.onCreate()
    Timber.plant(Timber.DebugTree())
    init()
  }

  private fun init() {
    DI.appComponent = DaggerAppComponent.builder()
      .appContext(this)
      .build()


    DI.networkComponent = DaggerNetworkComponent.create()

    DI.preferencesApi = PreferencesComponent.initAndGet(this)

    DI.databaseApi = DatabaseComponent.initAndGet(this)
  }

}