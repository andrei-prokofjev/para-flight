package com.apro.paraflight

import com.apro.core.db.api.di.DatabaseApi
import com.apro.paraflight.di.AppComponent

// test
object DI {
  lateinit var appComponent: AppComponent

  lateinit var databaseApi: DatabaseApi
}