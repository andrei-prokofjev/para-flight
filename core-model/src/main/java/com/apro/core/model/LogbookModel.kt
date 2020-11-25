package com.apro.core.model

import android.location.Location

data class LogbookModel(
  val name: String = "-",
  val duration: Long = 0L,
  val flightPoints: List<Location> = emptyList()
)
