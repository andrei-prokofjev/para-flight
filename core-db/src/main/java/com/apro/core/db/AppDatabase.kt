package com.apro.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.apro.core.db.api.data.DatabaseClientApi
import com.apro.core.db.entity.FlightPointEntity

@Database(entities = [FlightPointEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(), DatabaseClientApi {
  override fun inTransaction(block: () -> Unit) {
    runInTransaction { block.invoke() }
  }

  override fun clearAll() {
    clearAllTables()
  }
}