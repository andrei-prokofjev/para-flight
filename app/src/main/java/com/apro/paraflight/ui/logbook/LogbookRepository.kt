package com.apro.paraflight.ui.logbook

import com.apro.core.model.Logbook
import kotlinx.coroutines.flow.Flow

interface LogbookRepository {
  fun getLogbooks()
  suspend fun logbooksFlow(): Flow<List<Logbook>>
  fun clear()
}