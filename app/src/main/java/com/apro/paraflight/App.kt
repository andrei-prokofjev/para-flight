package com.apro.paraflight

import android.app.Application
import com.bumptech.glide.Glide.init
import timber.log.Timber

@Suppress("unused")
class App : Application() {

  override fun onCreate() {
    super.onCreate()
    Timber.plant(Timber.DebugTree())

  }


}