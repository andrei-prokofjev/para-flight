package com.apro.paraflight.ui.mapbox

import android.location.Location
import com.apro.core.location.engine.api.LocationEngine
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.paraflight.DI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapboxInteractorImpl @Inject constructor() : MapboxInteractor {

  private var locationEngine: LocationEngine? = null

  private val locationUpdatesChannel = ConflatedBroadcastChannel<Location>()
  override fun locationUpdatesFlow() = locationUpdatesChannel.asFlow()


  private val lastLocationChannel = ConflatedBroadcastChannel<Location>()
  override fun lastLocationFlow() = lastLocationChannel.asFlow()

  private val uiSettingsChannel = ConflatedBroadcastChannel<MapboxSettings>()
  override fun uiSettingsFlow() = uiSettingsChannel.asFlow()

  override var uiSettings: MapboxSettings
    get() = uiSettingsChannel.value
    set(value) {
      GlobalScope.launch { uiSettingsChannel.send(value) }
    }

  override fun requestLocationUpdates(locationEngine: LocationEngine) {
    this.locationEngine = locationEngine.apply { requestLocationUpdates() }

    GlobalScope.launch {
      locationEngine.updateLocationFlow().collect {
        locationUpdatesChannel.send(it)
      }
    }
  }

  override fun requestLastLocation(locationEngine: LocationEngine) {
    this.locationEngine = locationEngine.apply { requestLastLocation() }

    GlobalScope.launch(Dispatchers.Main) {
      locationEngine.lastLocationFlow().collect {
        lastLocationChannel.send(it)
      }
    }
  }

  suspend fun requestLastLocationa(locationEngine: LocationEngine) {
    this.locationEngine = locationEngine.apply { requestLastLocation() }

    GlobalScope.launch {
      locationEngine.lastLocationFlow().collect {
        lastLocationChannel.send(it)
      }
    }
  }


  override fun removeLocationUpdate() {
    locationEngine?.removeLocationUpdates()
    locationEngine = null
  }

  override fun changeMapStyle() {
    val mapboxPreferences = DI.preferencesApi.mapbox()
    val nextStyle = (mapboxPreferences.mapStyle.ordinal + 1) % MapboxPreferences.MapStyle.values().size
    val mapStyle = MapboxPreferences.MapStyle.values()[nextStyle]
    mapboxPreferences.mapStyle = mapStyle
  }


}