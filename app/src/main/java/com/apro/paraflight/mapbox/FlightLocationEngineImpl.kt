package com.apro.paraflight.mapbox

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.mapbox.android.core.location.LocationEngineCallback
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.android.core.location.LocationEngineResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class FlightLocationEngineImpl(val context: Context) : FlightLocationEngine {

  var locationEngine = LocationEngineProvider.getBestLocationEngine(context)

  private val locationChannel = ConflatedBroadcastChannel<Location>()

  var scope: CoroutineScope = CoroutineScope(Dispatchers.Default)

  private val locationUpdateCallback = object : LocationEngineCallback<LocationEngineResult> {
    override fun onSuccess(result: LocationEngineResult) {
      result.lastLocation?.let {
        Timber.d(">>> location update: $it")
        scope.launch { locationChannel.send(it) }
      }
    }

    override fun onFailure(exception: Exception) {
    }
  }

  override fun updateLocationFlow() = locationChannel.asFlow()

  @SuppressLint("MissingPermission")
  override fun requestLocationUpdates() {
    Timber.d(">>> requestLocationUpdates")
    val request = LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
      .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
      .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME)
      .build()

    locationEngine.requestLocationUpdates(request, locationUpdateCallback, context.mainLooper)
  }

  override fun removeLocationUpdates() {
    Timber.d(">>> removeLocationUpdates")
    locationEngine.removeLocationUpdates(locationUpdateCallback)
  }

  @SuppressLint("MissingPermission")
  override fun getLastLocation(callback: (Location) -> Unit) {
    locationEngine.getLastLocation(object : LocationEngineCallback<LocationEngineResult> {
      override fun onSuccess(result: LocationEngineResult) {
        result.lastLocation?.let { callback.invoke(it) }
      }

      override fun onFailure(exception: java.lang.Exception) {
      }
    })
  }

  override fun clear() {
    scope.launch { cancel() }
  }

  companion object {
    private const val DEFAULT_INTERVAL_IN_MILLISECONDS = 2000L
    private const val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5
  }
}