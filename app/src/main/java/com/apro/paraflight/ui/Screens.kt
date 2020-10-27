package com.apro.paraflight.ui

import com.apro.paraflight.ui.about.AboutFragment
import com.apro.paraflight.ui.flight.FlightFragment
import com.apro.paraflight.ui.logbook.LogbookFragment
import com.apro.paraflight.ui.main.MainFragment
import com.apro.paraflight.ui.preflight.PreflightFragment
import com.apro.paraflight.ui.profile.ProfileFragment
import com.apro.paraflight.ui.settings.SettingsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
  fun main() = FragmentScreen(MainFragment::javaClass.name) { MainFragment.create() }

  fun profile() = FragmentScreen(ProfileFragment::javaClass.name) { ProfileFragment.create() }

  fun settings() = FragmentScreen(SettingsFragment::javaClass.name) { SettingsFragment.create() }

  fun about() = FragmentScreen(AboutFragment::javaClass.name) { AboutFragment.create() }

  fun preflight() = FragmentScreen(PreflightFragment::javaClass.name) { PreflightFragment.create() }

  fun flight() = FragmentScreen(FlightFragment::javaClass.name) { FlightFragment.create() }

  fun logbook() = FragmentScreen(LogbookFragment::javaClass.name) { LogbookFragment.create() }
}