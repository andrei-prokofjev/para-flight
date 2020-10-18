package com.apro.core.db.api.data.store

import com.apro.core.model.FlightPointModel

interface FlightsStore {

  fun insertFlightPoints(points: List<FlightPointModel>)

  fun getFlightPoints(): List<FlightPointModel>

}