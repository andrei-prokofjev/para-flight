package com.apro.core.location.engine.impl

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.apro.core.location.engine.api.LocationEngine
import com.mapbox.android.core.location.LocationEngineCallback
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.android.core.location.LocationEngineResult
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import timber.log.Timber

class MapboxLocationEngine(private val context: Context) : LocationEngine {

  private var locationEngine = LocationEngineProvider.getBestLocationEngine(context)

  private val updateLocationChannel = ConflatedBroadcastChannel<Location>()
  override fun updateLocationFlow() = updateLocationChannel.asFlow()

  private val lastLocationChannel = ConflatedBroadcastChannel<Location>()
  override fun lastLocationFlow() = lastLocationChannel.asFlow()

  var scope: CoroutineScope? = null

  init {
    clear()
    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })
  }

  private val locationUpdateCallback = object : LocationEngineCallback<LocationEngineResult> {
    override fun onSuccess(result: LocationEngineResult) {
       Timber.d("location update: %s", result.lastLocation)
      result.lastLocation?.let {
        scope?.launch { updateLocationChannel.send(it) }
      }
    }

    override fun onFailure(exception: Exception) {
      Timber.e(">>> !!! location update error: $exception")
    }
  }


  @SuppressLint("MissingPermission")
  override fun requestLocationUpdates() {
    if (isLocationPermissionsDenied()) return

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
  override fun requestLastLocation() {
    if (isLocationPermissionsDenied()) return

    locationEngine.getLastLocation(object : LocationEngineCallback<LocationEngineResult> {
      override fun onSuccess(result: LocationEngineResult) {

        result.lastLocation?.let {
          scope?.launch { lastLocationChannel.send(it) }
        }
      }

      override fun onFailure(exception: java.lang.Exception) {
      }
    })
  }

  private fun isLocationPermissionsDenied(): Boolean {
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
      && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

      scope?.launch(Dispatchers.Main) {
        Toast.makeText(context, "LOCATION PERMISSIONS REQUIRED", Toast.LENGTH_LONG)
          .apply {
            setGravity(gravity, 0, 0)
            show()
          }
      }
      return true
    }
    return false
  }

  override fun clear() {
    scope?.launch { cancel() }
  }

  companion object {
    const val DEFAULT_INTERVAL_IN_MILLISECONDS = 500L
    const val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5
  }

}