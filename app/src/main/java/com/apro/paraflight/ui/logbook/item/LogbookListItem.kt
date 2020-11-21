package com.apro.paraflight.ui.logbook.item

import android.location.Location
import com.apro.core.ui.adapter.ListItem

data class LogbookListItem(
  val title: String,
  val flightPoints: List<Location>,
  val duration: String
) : ListItem