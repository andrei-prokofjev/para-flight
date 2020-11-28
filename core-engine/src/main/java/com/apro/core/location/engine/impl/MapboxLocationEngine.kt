package com.apro.core.location.engine.impl

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Context.SENSOR_SERVICE
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationProvider
import android.location.OnNmeaMessageListener
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.apro.core.location.engine.api.LocationEngine
import com.apro.core.location.engine.utils.NmeaUtils
import com.mapbox.android.core.location.LocationEngineCallback
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.android.core.location.LocationEngineResult
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@SuppressLint("ServiceCast")
class MapboxLocationEngine(private val context: Context) : LocationEngine {

  private var locationEngine = LocationEngineProvider.getBestLocationEngine(context)

  private val updateLocationChannel = ConflatedBroadcastChannel<Location>()
  override fun updateLocationFlow() = combine(
    updateLocationChannel.asFlow(), altitudeMslChannel.asFlow()) { location, altitude ->
    location.altitude = altitude ?: 0.0
    location
  }

  private val altitudeMslChannel = ConflatedBroadcastChannel<Double?>()


  var scope: CoroutineScope? = null

  private var locationManager: LocationManager?

  var gpsProvider: LocationProvider?


  private val nmeaListener = OnNmeaMessageListener { message, _ ->
    if (message.startsWith("\$GPGGA") || message.startsWith("\$GNGNS") || message.startsWith("\$GNGGA")) {
      scope?.launch(Dispatchers.Default) { altitudeMslChannel.send(NmeaUtils.getAltitudeMeanSeaLevel(message)) }
    }
  }

  init {
    clear()
    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })

    locationManager = context.getSystemService(LOCATION_SERVICE) as? LocationManager

    gpsProvider = locationManager?.getProvider(LocationManager.GPS_PROVIDER)

    val sensorManager = context.getSystemService(SENSOR_SERVICE) as? SensorManager
  }

  private val locationUpdateCallback = object : LocationEngineCallback<LocationEngineResult> {
    override fun onSuccess(result: LocationEngineResult) {
      Timber.d("location update: %s", result.lastLocation)
      result.lastLocation?.let {

        scope?.launch {
          updateLocationChannel.send(it)
        }
      }
    }

    override fun onFailure(exception: Exception) {
      Timber.e(">>> !!! location update error: $exception")
    }
  }


  @SuppressLint("MissingPermission")
  override fun requestLocationUpdates() {
    if (isLocationPermissionsDenied()) return

    addNmeaListenerAndroidN()

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
  override suspend fun lastLocation(): Location {
    return suspendCoroutine { c ->
      if (isLocationPermissionsDenied()) c.resumeWithException(Exception("permission not provided"))

      locationEngine.getLastLocation(object : LocationEngineCallback<LocationEngineResult> {
        override fun onSuccess(result: LocationEngineResult) {

          result.lastLocation?.let {
            c.resume(it)
          }
        }

        override fun onFailure(exception: Exception) {
          c.resumeWithException(exception)
        }
      })
    }
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


  @SuppressLint("MissingPermission")
  private fun addNmeaListenerAndroidN() {
    locationManager?.addNmeaListener(nmeaListener)
  }

  override fun clear() {
    locationManager?.removeNmeaListener(nmeaListener)
    scope?.launch { cancel() }
  }

  companion object {
    const val DEFAULT_INTERVAL_IN_MILLISECONDS = 500L
    const val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5
  }

}