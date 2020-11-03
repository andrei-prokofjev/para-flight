package com.apro.core.location.engine.impl

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.apro.core.location.engine.api.LocationEngine
import com.apro.core.location.engine.api.LocationEngine.Companion.DEFAULT_INTERVAL_IN_MILLISECONDS
import com.apro.core.util.Speed
import com.apro.core.util.kilometersPerHour
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import timber.log.Timber
import java.io.IOException
import java.io.InputStreamReader

class MapboxLocationEngine(private val context: Context) : LocationEngine {

  private val updateLocationChannel = ConflatedBroadcastChannel<Location>()
  override fun updateLocationFlow() = updateLocationChannel.asFlow()

  private val lastLocationChannel = ConflatedBroadcastChannel<Location>()
  override fun lastLocationFlow() = lastLocationChannel.asFlow()

  var scope: CoroutineScope? = null

  init {
    clear()
    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })
  }

  @SuppressLint("MissingPermission")
  override fun requestLocationUpdates() {
    Timber.d(">>> requestLocationUpdates")


    scope?.launch {
      val flight = readCsv("test_flight.csv")

      flight.forEach {
        delay(DEFAULT_INTERVAL_IN_MILLISECONDS)
        updateLocationChannel.send(it)
      }
    }
  }

  override fun removeLocationUpdates() {
    Timber.d(">>> removeLocationUpdates")

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


  private fun readCsv(path: String): List<Location> {
    val assets = context.assets
    val flight = mutableListOf<Location>()

    try {
      val csvStream = assets.open(path)
      val csvStreamReader = InputStreamReader(csvStream)
      val lines = csvStreamReader.readLines()
      lines.subList(1, lines.size).forEach {
        val data = it.split(",")
        val location = Location("mock")
        location.speed = data[1].toInt().kilometersPerHour.convertTo(Speed.MetersPerSecond).amount.toFloat()
        location.altitude = data[2].toDouble()
        flight.add(location)
      }
    } catch (e: IOException) {
      println(">>> !!! $e")
    }

    val list = emptyList<Location>()
    return flight
  }
}
