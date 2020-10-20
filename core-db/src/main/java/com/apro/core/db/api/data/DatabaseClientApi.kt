package com.apro.core.db.api.data

import com.apro.core.db.api.data.dao.RouteDao


interface DatabaseClientApi {
  fun inTransaction(block: () -> Unit)
  fun clearAll()

  fun flightDao(): RouteDao

}