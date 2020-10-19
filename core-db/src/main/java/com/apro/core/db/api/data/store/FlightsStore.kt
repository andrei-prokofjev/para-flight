package com.apro.core.db.api.data.store

import com.apro.core.db.model.FlightPointModel

interface FlightsStore {

  fun insertFlightPoints(points: List<FlightPointModel>)

  fun insertFlightPoint(point: FlightPointModel)

  fun getFlightPoints(): List<FlightPointModel>

}