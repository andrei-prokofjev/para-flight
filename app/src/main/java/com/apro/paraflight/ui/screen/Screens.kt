package com.apro.paraflight.ui.screen

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