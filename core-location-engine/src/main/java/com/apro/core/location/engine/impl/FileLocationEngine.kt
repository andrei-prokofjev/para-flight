package com.apro.core.location.engine.impl

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.apro.core.location.engine.api.LocationEngine
import com.apro.core.model.FlightModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class FileLocationEngine(private val context: Context) : LocationEngine {

  private val updateLocationChannel = ConflatedBroadcastChannel<Location>()
  override fun updateLocationFlow() = updateLocationChannel.asFlow()

  private val lastLocationChannel = ConflatedBroadcastChannel<Location>()
  override fun lastLocationFlow() = lastLocationChannel.asFlow()

  private val routeChannel = ConflatedBroadcastChannel<List<FlightModel>>()
  override fun routeFlow() = routeChannel.asFlow()


  var scope: CoroutineScope? = null

  init {
    clear()
    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })
  }


  @SuppressLint("MissingPermission")
  override fun requestLocationUpdates() {


    Timber.d(">>> requestLocationUpdates")

  }


  override fun removeLocationUpdates() {
    Timber.d(">>> removeLocationUpdates")
  }


  override fun getLastLocation() {

  }


  override fun clear() {
    scope?.launch { cancel() }
  }

  override fun updateRoute(flightData: List<FlightModel>) {
    scope?.launch { routeChannel.send(flightData) }
  }


}