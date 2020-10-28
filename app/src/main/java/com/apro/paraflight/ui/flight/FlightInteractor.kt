package com.apro.paraflight.ui.flight

import kotlinx.coroutines.flow.Flow

interface FlightInteractor {
  fun init()

  fun updateLocationFlow(): Flow<FlightPoint>

  fun clear()
}