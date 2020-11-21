package com.apro.paraflight.ui.logbook

import com.apro.core.ui.adapter.SimpleDiffAdapter
import com.apro.paraflight.ui.logbook.delegate.LogbookDelegate
import com.apro.paraflight.ui.logbook.item.LogbookListItem


class LogbookAdapter(
  private val onItemClick: (LogbookListItem) -> Unit
) : SimpleDiffAdapter() {

  init {
    delegatesManager.apply {
      addDelegate(LogbookDelegate(onItemClick))
    }
  }


}