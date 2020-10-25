package com.apro.core.db.impl.di

import android.content.Context
import com.apro.core.db.api.di.DatabaseApi
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DatabaseModule::class])
@Singleton
interface DatabaseComponent : DatabaseApi {

  companion object {
    private var databaseComponent: DatabaseComponent? = null

    fun create(context: Context): DatabaseApi {
      if (databaseComponent == null) {
        databaseComponent = DaggerDatabaseComponent.builder()
          .databaseModule(DatabaseModule(context))
          .build()
      }
      return requireNotNull(databaseComponent)
    }

    fun get(): DatabaseApi = requireNotNull(databaseComponent)
  }
}