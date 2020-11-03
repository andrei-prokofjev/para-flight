package com.apro.core.location.engine.impl

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.apro.core.location.engine.api.LocationEngine
import com.apro.core.location.engine.api.LocationEngine.Companion.DEFAULT_INTERVAL_IN_MILLISECONDS
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import timber.log.Timber

class MapboxLocationEngine(private val context: Context) : LocationEngine {


  override val updateLocationChannel = ConflatedBroadcastChannel<Location>()

  override val lastLocationChannel = ConflatedBroadcastChannel<Location>()


  var scope: CoroutineScope? = null

  var requestLocationUpdate = false

  init {
    println(">>> mock$")
    clear()
    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })


  }


  @SuppressLint("MissingPermission")
  override fun requestLocationUpdates() {
    Timber.d(">>> requestLocationUpdates")
    requestLocationUpdate = true

    scope?.launch {

      while (requestLocationUpdate) {
        delay(DEFAULT_INTERVAL_IN_MILLISECONDS)
        println(">>> send$")
        updateLocationChannel.send(Location("mock"))
      }
    }
  }

  override fun removeLocationUpdates() {
    Timber.d(">>> removeLocationUpdates")
    requestLocationUpdate = false
  }


  @SuppressLint("MissingPermission")
  override fun getLastLocation() {
    scope?.launch {
      lastLocationChannel.send(Location("mock"))
    }
  }


  override fun clear() {
    scope?.launch { cancel() }
  }


}