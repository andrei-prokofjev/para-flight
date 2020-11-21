package com.apro.paraflight.ui.logbook

import com.apro.core.ui.adapter.ListItem
import kotlinx.coroutines.flow.Flow

interface LogbookInteractor {
  suspend fun logbooksFlow(): Flow<List<ListItem>>
  fun getLogbooks()
  fun clear()
}