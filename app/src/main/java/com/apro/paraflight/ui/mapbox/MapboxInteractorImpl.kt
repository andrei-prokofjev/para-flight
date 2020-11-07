package com.apro.paraflight.ui.mapbox

import com.apro.core.location.engine.api.LocationEngine
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core.util.event.EventBus
import com.apro.paraflight.events.UpdateLocationEvent
import com.mapbox.geojson.Point
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

class MapboxInteractorImpl @Inject constructor(
  private val mapboxPreferences: MapboxPreferences,
  private val locationEngine: LocationEngine,
  private val eventBus: EventBus
) : MapboxInteractor {

  private var scope: CoroutineScope? = null


  private val mapStyleChannel = ConflatedBroadcastChannel<MapboxPreferences.MapStyle>()
  override fun mapStyleFlow() = mapStyleChannel.asFlow()

  private val routeLocationChannel = ConflatedBroadcastChannel<List<Point>>()
  override fun routeLocationFlow() = routeLocationChannel.asFlow()


  init {
    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })

    scope?.launch {
      locationEngine.updateLocationFlow().collect { eventBus.send(UpdateLocationEvent(it)) }
    }

    scope?.launch {
      locationEngine.lastLocationFlow().collect { eventBus.send(UpdateLocationEvent(it)) }
    }

    scope?.launch {
      mapboxPreferences.mapStyleFlow().collect { mapStyleChannel.send(it) }
    }
  }

  override fun changeMapStyle() {
    val nextStyle = (mapboxPreferences.mapStyle.ordinal + 1) % MapboxPreferences.MapStyle.values().size
    val mapStyle = MapboxPreferences.MapStyle.values()[nextStyle]
    mapboxPreferences.mapStyle = mapStyle
  }

  override fun navigateToCurrentPosition() {
    locationEngine.getLastLocation()
  }

  override fun clear() {
    scope?.coroutineContext?.cancelChildren()
    scope?.launch { cancel() }
    locationEngine.removeLocationUpdates()
    locationEngine.clear()
  }
}