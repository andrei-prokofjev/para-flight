package com.apro.core.db.api.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apro.core.db.entity.LocationPointEntity

@Dao
interface RouteDao {

  @Query("SELECT * FROM routes")
  fun getAll(): List<LocationPointEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(points: List<LocationPointEntity>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(point: LocationPointEntity)
}