package com.apro.core.db.impl.di

import android.content.Context
import androidx.room.Room
import com.apro.core.db.AppDatabase
import com.apro.core.db.api.data.DatabaseClientApi
import com.apro.core.db.api.data.store.Cleaner
import com.apro.core.db.api.data.store.RouteStore
import com.apro.core.db.impl.store.CleanerImpl
import com.apro.core.db.impl.store.RouteStoreImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context) {

  @Singleton
  @Provides
  internal fun provideDatabaseClientApi(): DatabaseClientApi {
    return Room.databaseBuilder(context, AppDatabase::class.java, "paraflight_db")
      .fallbackToDestructiveMigration()
      .build()
  }

  @Singleton
  @Provides
  fun provideCleaner(dbApi: DatabaseClientApi): Cleaner = CleanerImpl(dbApi)

  @Singleton
  @Provides
  fun provideThreadStore(dbApi: DatabaseClientApi): RouteStore = RouteStoreImpl(dbApi)
}