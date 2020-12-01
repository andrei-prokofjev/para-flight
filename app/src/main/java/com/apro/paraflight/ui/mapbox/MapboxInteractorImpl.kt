package com.apro.paraflight.ui.mapbox

import android.location.Location
import com.apro.core.location.engine.api.LocationEngine
import com.apro.core.location.engine.model.DilutionOfPrecision
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.paraflight.DI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapboxInteractorImpl @Inject constructor() : MapboxInteractor {

  private var locationEngine: LocationEngine? = null

  private val locationUpdatesChannel = ConflatedBroadcastChannel<Location>()
  override fun locationUpdatesFlow() = locationUpdatesChannel.asFlow()


  private val lastLocationChannel = ConflatedBroadcastChannel<Location>()
  override fun lastLocationFlow() = lastLocationChannel.asFlow()

  private val mapboxSettingsChannel = ConflatedBroadcastChannel<MapboxSettings>()
  override fun mapboxSettingsFlow() = mapboxSettingsChannel.asFlow()

  private val dopChannel = ConflatedBroadcastChannel<DilutionOfPrecision?>()
  override fun dopFlow() = dopChannel.asFlow()
  override fun calibrate() {
    locationEngine?.calibrate()
  }

  override var mapboxSettings: MapboxSettings
    get() = mapboxSettingsChannel.value
    set(value) {
      GlobalScope.launch { mapboxSettingsChannel.send(value) }
    }

  override fun requestLocationUpdates(locationEngine: LocationEngine) {
    this.locationEngine = locationEngine.apply { requestLocationUpdates() }

    GlobalScope.launch {
      locationEngine.dopFlow().collect {
        dopChannel.send(it)
      }
    }

    GlobalScope.launch {
      locationEngine.updateLocationFlow().collect {
        locationUpdatesChannel.send(it)
      }
    }
  }

  override fun requestLastLocation(locationEngine: LocationEngine) {
    GlobalScope.launch(Dispatchers.IO) {
      lastLocationChannel.send(locationEngine.lastLocation())
    }
  }

  override fun removeLocationUpdate() {
    locationEngine?.removeLocationUpdates()
    locationEngine = null
  }

  override fun changeMapStyle() {
    val mapboxPreferences = DI.appComponent.mapboxPreferences()
    val nextStyle = (mapboxPreferences.mapStyle.ordinal + 1) % MapboxPreferences.MapStyle.values().size
    val mapStyle = MapboxPreferences.MapStyle.values()[nextStyle]
    mapboxPreferences.mapStyle = mapStyle
  }

  override fun getEngine(): LocationEngine? {
    return locationEngine
  }


}