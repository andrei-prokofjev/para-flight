package com.apro.core.location.engine.impl

import android.annotation.SuppressLint
import android.location.Location
import com.apro.core.location.engine.api.LocationEngine
import com.apro.core.location.engine.model.DilutionOfPrecision
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import timber.log.Timber

class ReplayLocationEngine(
  private val flightPoints: List<Location>
) : LocationEngine {

  private val updateLocationChannel = ConflatedBroadcastChannel<Location>()
  override fun updateLocationFlow() = updateLocationChannel.asFlow()

  var scope: CoroutineScope? = null

  init {
    clear()
    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })
  }


  @SuppressLint("MissingPermission")
  override fun requestLocationUpdates() {
    Timber.d(">>> requestLocationUpdates")


    scope?.launch {
      flightPoints.forEach {
        updateLocationChannel.send(it)
        delay(200)
      }

      removeLocationUpdates()
    }
  }

  override fun removeLocationUpdates() {


    Timber.d(">>> removeLocationUpdates")

    clear()
  }

  override suspend fun lastLocation() = Location("replay")

  override fun dopFlow(): Flow<DilutionOfPrecision?> {
    TODO("Not yet implemented")
  }


  override fun clear() {
    scope?.coroutineContext?.cancelChildren()
    scope?.launch { cancel() }
  }
}