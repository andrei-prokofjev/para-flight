package com.apro.paraflight

import android.app.Application
import com.apro.core.network.di.DaggerNetworkComponent
import com.apro.paraflight.di.DaggerAppComponent
import com.apro.paraflight.preferences.di.PreferencesComponent
import timber.log.Timber

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
  }

}