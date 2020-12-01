package com.apro.core.location.engine.impl

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.apro.core.location.engine.api.LocationEngine
import com.apro.core.location.engine.model.DilutionOfPrecision
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

class FileLocationEngine(val context: Context, private val file: File) : LocationEngine {

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

    val data = readCsv(file.path)
    scope?.launch {
      data.forEach {

        updateLocationChannel.send(it)
        delay(100)
      }

      removeLocationUpdates()
    }
  }

  override fun removeLocationUpdates() {


    Timber.d(">>> removeLocationUpdates")

    clear()
  }

  override suspend fun lastLocation() = Location(file.name)
  override fun dopFlow(): Flow<DilutionOfPrecision?> {
    TODO("Not yet implemented")
  }


  override fun clear() {
    scope?.coroutineContext?.cancelChildren()
    scope?.launch { cancel() }
  }

  override fun calibrate() {

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
        location.latitude = data[0].toDouble()
        location.longitude = data[1].toDouble()
        location.time = data[2].toLong()
        location.altitude = data[3].toDouble()
        location.speed = data[4].toFloat()
        location.bearing = data[5].toFloat()
        flight.add(location)
      }
    } catch (e: IOException) {
      println(">>> !!! $e")
    }

    val list = emptyList<Location>()
    return flight
  }

}