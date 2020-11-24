package com.apro.paraflight.ui

import com.apro.core.location.engine.api.LocationEngine
import com.apro.paraflight.ui.about.AboutFragment
import com.apro.paraflight.ui.about.AboutScreenComponent
import com.apro.paraflight.ui.flight.FlightFragment
import com.apro.paraflight.ui.flight.FlightScreenComponent
import com.apro.paraflight.ui.logbook.LogbookFragment
import com.apro.paraflight.ui.logbook.LogbookScreenComponent
import com.apro.paraflight.ui.main.MainFragment
import com.apro.paraflight.ui.main.MainScreenComponent
import com.apro.paraflight.ui.mapbox.MapboxSettings
import com.apro.paraflight.ui.preflight.PreflightFragment
import com.apro.paraflight.ui.profile.ProfileFragment
import com.apro.paraflight.ui.settings.SettingsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
  fun main(engine: LocationEngine) = FragmentScreen(MainFragment::javaClass.name) {
    MainFragment.create(MainScreenComponent.create(engine))
  }

  fun profile() = FragmentScreen(ProfileFragment::javaClass.name) { ProfileFragment.create() }

  fun settings() = FragmentScreen(SettingsFragment::javaClass.name) { SettingsFragment.create() }

  fun about() = FragmentScreen(AboutFragment::javaClass.name) { AboutFragment.create(AboutScreenComponent.create()) }

  fun preflight() = FragmentScreen(PreflightFragment::javaClass.name) { PreflightFragment.create() }

  fun flight(engine: LocationEngine, settings: MapboxSettings = MapboxSettings.DefaultMapboxSettings) = FragmentScreen(FlightFragment::javaClass.name) {
    FlightFragment.create(FlightScreenComponent.create(engine, settings))
  }

  fun logbook() = FragmentScreen(LogbookFragment::javaClass.name) { LogbookFragment.create(LogbookScreenComponent.create()) }
}