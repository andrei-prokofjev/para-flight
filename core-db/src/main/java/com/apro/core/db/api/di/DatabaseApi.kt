package com.apro.core.db.api.di

import com.apro.core.db.api.data.store.Cleaner
import com.apro.core.db.api.data.store.FlightsStore

interface DatabaseApi {
  fun cleaner(): Cleaner
  fun flightsStore(): FlightsStore
}