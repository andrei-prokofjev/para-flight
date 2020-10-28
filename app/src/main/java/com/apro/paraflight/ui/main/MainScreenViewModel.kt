package com.apro.paraflight.ui.main

import com.apro.core.navigation.AppRouter
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core.ui.BaseViewModel
import com.apro.core.util.event.EventBus
import com.apro.core.voiceguidance.api.VoiceGuidance
import com.apro.paraflight.events.MyLocationEvent
import com.apro.paraflight.mapbox.FlightLocationRepository
import com.apro.paraflight.ui.Screens
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
  val appRouter: AppRouter,
  private val eventBus: EventBus,
  private val mapboxPreferences: MapboxPreferences,
  private val locationRepository: FlightLocationRepository,
  private val voiceGuidance: VoiceGuidance
) : BaseViewModel() {


  fun onProfileClick() {
    appRouter.navigateTo(Screens.profile())
  }

  fun onLogbookClick() {
    appRouter.navigateTo(Screens.logbook())
  }

  fun onPreflightClick() {
//    appRouter.navigateTo(Screens.preflight())

    voiceGuidance.speak("Hello world")
  }

  fun onLayerClick() {
    val nextStyle = (mapboxPreferences.mapStyle.ordinal + 1) % MapboxPreferences.MapStyle.values().size
    val mapStyle = MapboxPreferences.MapStyle.values()[nextStyle]
    mapboxPreferences.mapStyle = mapStyle
  }

  fun onMyLocationClick() {
    locationRepository.getLastLocation { location ->
      location.let {
        val position = CameraPosition.Builder()
          .target(LatLng(it.latitude, it.longitude))
          .zoom(12.0)
          .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(position)
        eventBus.send(MyLocationEvent(cameraUpdate, 1000))
      }
    }
  }

}