package com.apro.paraflight.ui.flight

import kotlinx.coroutines.flow.Flow

interface FlightInteractor {

  enum class FlightState {
    PREPARING,
    TAKE_OFF,
    FLIGHT,
    LANDED
  }

  fun init()

  fun flightStateFlow(): Flow<FlightState>
  fun updateLocationFlow(): Flow<FlightModel>
  fun clear()
}