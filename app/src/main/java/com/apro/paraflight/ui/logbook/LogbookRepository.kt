package com.apro.paraflight.ui.logbook

import com.apro.core.model.LogbookModel
import kotlinx.coroutines.flow.Flow

interface LogbookRepository {
  fun getLogbooks()
  suspend fun logbooksFlow(): Flow<List<LogbookModel>>
  fun clear()
}