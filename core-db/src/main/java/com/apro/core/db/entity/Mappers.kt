package com.apro.core.db.entity

import com.apro.core.db.model.LocationPointModel

fun LocationPointEntity.model() = LocationPointModel(
  time = time,
  latitude = latitude,
  longitude = longitude,
  altitude = altitude
)
