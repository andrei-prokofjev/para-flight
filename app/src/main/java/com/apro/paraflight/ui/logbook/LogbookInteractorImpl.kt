package com.apro.paraflight.ui.logbook

import com.apro.core.ui.adapter.ListItem
import com.apro.paraflight.ui.logbook.item.LogbookListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class LogbookInteractorImpl @Inject constructor(
  private val logbookRepository: LogbookRepository
) : LogbookInteractor {

  override suspend fun logbooksFlow(): Flow<List<ListItem>> =
    logbookRepository.logbooksFlow().transform {
      emit(it.map {
        LogbookListItem(
          title = it.name,
          flightPoints = it.flightPoints,
          duration = it.duration.toString()
        )
      })
    }

  override fun getLogbooks() {
    logbookRepository.getLogbooks()
  }

  override fun clear() {
    logbookRepository.clear()
  }


}