package com.apro.paraflight

import android.app.Application
import com.apro.core.db.impl.di.DatabaseComponent
import com.apro.paraflight.di.AppComponent
import timber.log.Timber

@Suppress("unused")
class App : Application() {

  override fun onCreate() {
    super.onCreate()
    Timber.plant(Timber.DebugTree())
    init()
  }

  private fun init() {
    DI.appComponent = AppComponent.create(this)

    DI.databaseApi = DatabaseComponent.create(this)
  }

}