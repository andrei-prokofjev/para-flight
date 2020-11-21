package com.apro.core.model

import android.location.Location

data class Logbook(
  val name: String = "-",
  val duration: Long = 0L,
  val flightPoints: List<Location> = emptyList()
)
