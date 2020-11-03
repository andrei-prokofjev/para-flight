package com.apro.core.location.engine.api

import android.location.Location
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow

interface LocationEngine {
  fun requestLocationUpdates()
  fun removeLocationUpdates()

  fun getLastLocation()
  val lastLocationChannel: ConflatedBroadcastChannel<Location>
  fun lastLocationFlow() = lastLocationChannel.asFlow()


  val updateLocationChannel: ConflatedBroadcastChannel<Location>
  fun updateLocationFlow() = updateLocationChannel.asFlow()

  fun clear()

  companion object {
    const val DEFAULT_INTERVAL_IN_MILLISECONDS = 500L
    const val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5
  }
}