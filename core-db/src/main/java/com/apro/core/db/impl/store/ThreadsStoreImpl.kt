package com.apro.core.db.impl.store

import com.apro.core.db.api.data.DatabaseClientApi
import com.apro.core.db.api.data.store.RouteStore
import com.apro.core.db.entity.LocationPointEntity
import com.apro.core.db.entity.model
import com.apro.core.db.model.LocationPointModel
import javax.inject.Inject

class ThreadsStoreImpl @Inject constructor(
  private val db: DatabaseClientApi
) : RouteStore {

  override fun insertRoute(points: List<LocationPointModel>) {
    db.inTransaction {
      val entities = points.map { LocationPointEntity.from(it) }
      db.flightDao().insertAll(entities)
    }
  }

  override fun insertLocationPoint(point: LocationPointModel) {
    db.inTransaction {
      db.flightDao().insert(LocationPointEntity.from(point))
    }
  }

  override fun getRoute(): List<LocationPointModel> = db.flightDao().getAll().map { it.model() }

}