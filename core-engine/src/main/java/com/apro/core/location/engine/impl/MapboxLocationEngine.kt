package com.apro.core.location.engine.impl

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationProvider
import android.location.OnNmeaMessageListener
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.apro.core.location.engine.api.LocationEngine
import com.apro.core.location.engine.model.DilutionOfPrecision
import com.apro.core.location.engine.utils.NmeaUtils
import com.mapbox.android.core.location.LocationEngineCallback
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.android.core.location.LocationEngineResult
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MapboxLocationEngine(private val context: Context) : LocationEngine {

  private var locationEngine = LocationEngineProvider.getBestLocationEngine(context)

  private val updateLocationChannel = ConflatedBroadcastChannel<Location>()
  override fun updateLocationFlow() = updateLocationChannel.asFlow()

  private val altitudeMslChannel = ConflatedBroadcastChannel<Double?>()

  private val dopChannel = ConflatedBroadcastChannel<DilutionOfPrecision?>()
  private val pressureChannel = ConflatedBroadcastChannel<Float?>()


  override fun dopFlow() = dopChannel.asFlow()

  private var sensorManager: SensorManager? = null
  private var pressureSensor: Sensor? = null


  var scope: CoroutineScope? = null

  private var locationManager: LocationManager?

  private var gpsProvider: LocationProvider?

  private val refAltitudes = mutableListOf<Double>() // calculate average base altitudes
  var refAltitude = 0.0

  private val refPressures = mutableListOf<Float>() // calculate average base pressures
  private var refPressure = 0f


  private val nmeaListener = OnNmeaMessageListener { message, _ ->
    if (message.startsWith("\$GPGGA") || message.startsWith("\$GNGNS") || message.startsWith("\$GNGGA")) {
      val amsl = NmeaUtils.getAltitudeMeanSeaLevel(message)

      scope?.launch(Dispatchers.Default) {
        altitudeMslChannel.send(amsl)
      }
    }

    if (message.startsWith("\$GNGSA") || message.startsWith("\$GPGSA")) {
      val dop = NmeaUtils.getDop(message)
      dop?.let {
        if (it.verticalDop < MIN_GPS_QUALITY_LEVEL) {
          altitudeMslChannel.valueOrNull?.let {
            if (refAltitudes.size < ALTITUDE_PRECISION) {
              refAltitudes.add(it)
              if (refAltitudes.size == ALTITUDE_PRECISION) {
                var sum = 0.0
                refAltitudes.forEach { sum += it }
                refAltitude = sum / ALTITUDE_PRECISION
              }
            }
          }
        } else {
          refAltitudes.clear()
        }
      }

      scope?.launch(Dispatchers.Default) {
        if (refPressure > 0.0) {
          dopChannel.send(dop?.copy(baseAlt = refAltitude))
        } else dopChannel.send(dop)
      }
    }
  }

  private val sensorEventListener = object : SensorEventListener {
    override fun onSensorChanged(sensorEvent: SensorEvent) {
      dopChannel.valueOrNull?.let {
        if (it.verticalDop < MIN_GPS_QUALITY_LEVEL) {
          if (refPressures.size < PRESSURE_PRECISION) {
            refPressures.add(sensorEvent.values[0])
            if (refPressures.size == PRESSURE_PRECISION) {
              var sum = 0f
              refPressures.forEach { sum += it }
              refPressure = sum / PRESSURE_PRECISION
            }
          }
        }
      }

      scope?.launch(Dispatchers.IO) { pressureChannel.send(sensorEvent.values[0]) }
    }

    override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
  }

  private val locationUpdateCallback = object : LocationEngineCallback<LocationEngineResult> {
    override fun onSuccess(result: LocationEngineResult) {
      Timber.d("location update: %s", result.lastLocation)
      result.lastLocation?.let { loc ->

//        println(">>> baseAltitude: $refAltitude")
//        println(">>> base pressure: $refPressure")

        scope?.launch {
          loc.altitude = pressureChannel.valueOrNull?.let {
            if (refAltitude > 0.0 && refPressure > 0f) {
              refAltitude + SensorManager.getAltitude(refPressure, it)
            } else altitudeMslChannel.valueOrNull ?: 0.0
          } ?: altitudeMslChannel.valueOrNull ?: 0.0

          updateLocationChannel.send(loc)
        }
      }
    }

    override fun onFailure(exception: Exception) {
      Timber.e(">>> !!! location update error: $exception")
    }
  }

  init {
    clear()
    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })

    locationManager = ContextCompat.getSystemService(context, LocationManager::class.java)
    sensorManager = ContextCompat.getSystemService(context, SensorManager::class.java)
    pressureSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_PRESSURE)

    gpsProvider = locationManager?.getProvider(LocationManager.GPS_PROVIDER)
  }

  override fun calibrate() {
    refPressure = 0f
    refAltitude = 0.0
    refPressures.clear()
    refAltitudes.clear()
  }

  @SuppressLint("MissingPermission")
  override fun requestLocationUpdates() {
    if (isLocationPermissionsDenied()) return

    Timber.d(">>> requestLocationUpdates")

    pressureSensor?.let {
      sensorManager?.registerListener(sensorEventListener, it, SensorManager.SENSOR_DELAY_NORMAL)
    }
    locationManager?.addNmeaListener(nmeaListener)

    val request = LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
      .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
      .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME)
      .build()
    locationEngine.requestLocationUpdates(request, locationUpdateCallback, context.mainLooper)
  }


  override fun removeLocationUpdates() {
    Timber.d(">>> removeLocationUpdates")

    sensorManager?.unregisterListener(sensorEventListener)
    locationManager?.removeNmeaListener(nmeaListener)
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


  override fun clear() {
    scope?.launch { cancel() }
  }

  companion object {
    const val DEFAULT_INTERVAL_IN_MILLISECONDS = 2000L
    const val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5

    const val ALTITUDE_PRECISION = 30
    const val PRESSURE_PRECISION = 30
    const val MIN_GPS_QUALITY_LEVEL = 3
  }

}