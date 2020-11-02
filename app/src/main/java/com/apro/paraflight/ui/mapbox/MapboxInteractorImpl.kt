package com.apro.paraflight.ui.mapbox

import android.location.Location
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.paraflight.mapbox.MapboxLocationEngineRepository
import com.mapbox.geojson.Point
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

class MapboxInteractorImpl @Inject constructor(
  private val mapboxPreferences: MapboxPreferences,
  private val mapboxRepository: MapboxLocationEngineRepository
) : MapboxInteractor {

  private var scope: CoroutineScope? = null


  private val mapStyleChannel = ConflatedBroadcastChannel<MapboxPreferences.MapStyle>()
  override fun mapStyleFlow() = mapStyleChannel.asFlow()

  private val updateLocationChannel = ConflatedBroadcastChannel<Location>()
  override fun updateLocationFlow() = updateLocationChannel.asFlow()

  private val routeLocationChannel = ConflatedBroadcastChannel<List<Point>>()
  override fun routeLocationFlow() = routeLocationChannel.asFlow()


  init {
    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })

    scope?.launch {
      mapboxPreferences.mapStyleFlow().collect {
        mapStyleChannel.send(it)
      }
    }

    scope?.launch {
      mapboxRepository.lastLocationFlow().collect {
        updateLocationChannel.send(it)
      }
    }

    scope?.launch {
      mapboxRepository.routeFlow().collect {
        routeLocationChannel.send(it.map { Point.fromLngLat(it.lng, it.lat) })
      }
    }

    scope?.launch {
      mapboxRepository.updateLocationFlow().collect {
        updateLocationChannel.send(it)
      }
    }
  }

  override fun changeMapStyle() {
    val nextStyle = (mapboxPreferences.mapStyle.ordinal + 1) % MapboxPreferences.MapStyle.values().size
    val mapStyle = MapboxPreferences.MapStyle.values()[nextStyle]
    mapboxPreferences.mapStyle = mapStyle
  }

  override fun navigateToCurrentPosition() {
    mapboxRepository.getLastLocation()
  }

  override fun clear() {
    scope?.coroutineContext?.cancelChildren()
    scope?.launch { cancel() }
    mapboxRepository.removeLocationUpdates()
    mapboxRepository.clear()
  }
}