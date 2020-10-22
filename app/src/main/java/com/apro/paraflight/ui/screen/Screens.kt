package com.apro.paraflight.ui.screen

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.mapbox.mapboxsdk.maps.MapboxMapOptions

object Screens {
  fun settings() = FragmentScreen(SettingsFragment::javaClass.name) { SettingsFragment.newInstance() }
  fun main(options: MapboxMapOptions) = FragmentScreen(MainFragment::javaClass.name) { MainFragment.newInstance(options) }
  fun preflight() = FragmentScreen(PreflightFragment::javaClass.name) { PreflightFragment.newInstance() }
  fun flight() = FragmentScreen(FlightFragment::javaClass.name) { FlightFragment.newInstance() }
}