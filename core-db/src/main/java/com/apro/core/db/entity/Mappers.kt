package com.apro.core.db.entity

import com.apro.core.db.model.FlightPointModel

fun FlightPointEntity.model() = FlightPointModel(
  time = time,
  latitude = latitude,
  longitude = longitude,
  altitude = altitude
)
