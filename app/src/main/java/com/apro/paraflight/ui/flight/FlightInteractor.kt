package com.apro.paraflight.ui.flight

import com.apro.core.model.FlightModel
import kotlinx.coroutines.flow.Flow

interface FlightInteractor {

  enum class FlightState {
    PREPARING,
    TAKE_OFF,
    FLIGHT,
    LANDED
  }

  fun clear()
  val testFlow: Flow<String>

  fun flightDataFlow(): Flow<FlightModel>
}