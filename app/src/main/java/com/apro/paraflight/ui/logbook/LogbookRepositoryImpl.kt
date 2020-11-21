package com.apro.paraflight.ui.logbook

import android.location.Location
import com.apro.core.model.Logbook
import com.apro.paraflight.DI
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import timber.log.Timber
import java.io.InputStreamReader
import javax.inject.Inject

class LogbookRepositoryImpl @Inject constructor() : LogbookRepository {

  private val logbooksChannel = ConflatedBroadcastChannel<List<Logbook>>()

  private var scope: CoroutineScope? = null

  init {
    clear()
    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })
  }

  override fun getLogbooks() {
    DI.appComponent.resources().getAssetManager().apply {
      list("")?.filter { it.endsWith(".csv") }?.let {

        val logbooks = mutableListOf<Logbook>()

        it.forEach {
          try {
            val csvStream = open(it)
            val csvStreamReader = InputStreamReader(csvStream)
            val lines = csvStreamReader.readLines()
            val flightPoints = mutableListOf<Location>()
            lines.subList(1, lines.size).forEach {
              val data = it.split(",")
              val location = Location(it)
              location.latitude = data[0].toDouble()
              location.longitude = data[1].toDouble()
              location.time = data[2].toLong()
              location.altitude = data[3].toDouble()
              location.speed = data[4].toFloat()
              location.bearing = data[5].toFloat()
              flightPoints.add(location)
            }

            val start = lines[1][2].toLong()
            val end = lines.last()[2].toLong()
            logbooks.add(Logbook(
              name = it,
              duration = end - start,
              flightPoints = flightPoints
            ))

          } catch (e: Exception) {
            println(">>> !!! $e")
          }
        }
        scope?.launch(Dispatchers.IO) {
          logbooksChannel.send(logbooks)
        }
      }
    }
  }


  override suspend fun logbooksFlow(): Flow<List<Logbook>> = logbooksChannel.asFlow()

  override fun clear() {
    scope?.launch { cancel() }
  }

}