package com.apro.core.db.impl.store

import com.apro.core.db.api.data.DatabaseClientApi
import com.apro.core.db.api.data.store.FlightsStore
import com.apro.core.db.entity.FlightPointEntity
import com.apro.core.db.entity.model
import com.apro.core.db.model.FlightPointModel
import javax.inject.Inject

class ThreadsStoreImpl @Inject constructor(
  private val db: DatabaseClientApi
) : FlightsStore {

  override fun insertFlightPoints(points: List<FlightPointModel>) {
    db.inTransaction {
      val entities = points.map { FlightPointEntity.from(it) }
      db.flightDao().insertAll(entities)
    }
  }

  override fun insertFlightPoint(point: FlightPointModel) {
    db.inTransaction {
      db.flightDao().insert(FlightPointEntity.from(point))
    }
  }

  override fun getFlightPoints(): List<FlightPointModel> = db.flightDao().getAll().map { it.model() }

}