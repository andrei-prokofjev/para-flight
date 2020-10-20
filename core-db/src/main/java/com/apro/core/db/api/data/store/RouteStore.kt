package com.apro.core.db.api.data.store

import com.apro.core.db.model.LocationPointModel

interface RouteStore {

  fun insertRoute(points: List<LocationPointModel>)

  fun insertLocationPoint(point: LocationPointModel)

  fun getRoute(): List<LocationPointModel>

}