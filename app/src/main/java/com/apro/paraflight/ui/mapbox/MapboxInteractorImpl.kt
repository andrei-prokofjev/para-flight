package com.apro.paraflight.ui.mapbox

import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.paraflight.mapbox.MapboxLocationEngineRepository
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdate
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.Style
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

  private val cameraPositionChannel = ConflatedBroadcastChannel<CameraUpdate>()
  override val cameraPositionFlow = cameraPositionChannel.asFlow()

  private val mapStyleChannel = ConflatedBroadcastChannel<String>()
  override val mapStyleFlow = mapStyleChannel.asFlow()

  init {
    clear()
    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })

    scope?.launch {
      mapboxPreferences.mapStyleFlow().collect {
        mapStyleChannel.send(it.toStyle())
      }
    }
  }

  override fun changeMapStyle() {
    val nextStyle = (mapboxPreferences.mapStyle.ordinal + 1) % MapboxPreferences.MapStyle.values().size
    val mapStyle = MapboxPreferences.MapStyle.values()[nextStyle]
    mapboxPreferences.mapStyle = mapStyle
  }

  override fun navigateToCurrentPosition() {
    mapboxRepository.getLastLocation { location ->
      location.let {
        val position = CameraPosition.Builder()
          .target(LatLng(it.latitude, it.longitude))
          .zoom(12.0)
          .build()
        scope?.launch {
          println(">>> aaaa")
          cameraPositionChannel.send(CameraUpdateFactory.newCameraPosition(position))
        }
      }
    }
  }

  fun MapboxPreferences.MapStyle.toStyle(): String {
    return when (this) {
      MapboxPreferences.MapStyle.SATELLITE -> Style.SATELLITE
      MapboxPreferences.MapStyle.MAPBOX_STREETS -> Style.MAPBOX_STREETS
      MapboxPreferences.MapStyle.LIGHT -> Style.LIGHT
    }
  }

  override fun clear() {
    scope?.coroutineContext?.cancelChildren()
    scope?.launch { cancel() }
    mapboxRepository.removeLocationUpdates()
    mapboxRepository.clear()
  }
}