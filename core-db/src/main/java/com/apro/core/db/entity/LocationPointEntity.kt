package com.apro.core.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.apro.core.db.model.LocationPointModel

@Entity(tableName = "routes")
data class LocationPointEntity(
  @PrimaryKey val time: Long,
  @ColumnInfo(name = "latitude") val latitude: Double,
  @ColumnInfo(name = "longitude") val longitude: Double,
  @ColumnInfo(name = "altitude") val altitude: Double
) {
  companion object {
    fun from(model: LocationPointModel) = with(model) {
      LocationPointEntity(
        time = time,
        latitude = latitude,
        longitude = longitude,
        altitude = altitude
      )
    }
  }
}