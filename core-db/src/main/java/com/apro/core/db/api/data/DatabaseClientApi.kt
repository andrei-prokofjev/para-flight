package com.apro.core.db.api.data

import com.apro.core.db.api.data.dao.FlightDao


interface DatabaseClientApi {
  fun inTransaction(block: () -> Unit)
  fun clearAll()

  fun flightDao(): FlightDao

}