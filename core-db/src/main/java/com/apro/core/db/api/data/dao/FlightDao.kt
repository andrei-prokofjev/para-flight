package com.apro.core.db.api.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apro.core.db.entity.FlightPointEntity

@Dao
interface FlightDao {

  @Query("SELECT * FROM flights")
  fun getAll(): List<FlightPointEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(users: List<FlightPointEntity>)

}