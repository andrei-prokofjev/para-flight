package com.apro.paraflight.ui.screen

import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
  fun main() = FragmentScreen(MainFragment::javaClass.name) { MainFragment.create() }
  fun settings() = FragmentScreen(SettingsFragment::javaClass.name) { SettingsFragment.create() }
  fun preflight() = FragmentScreen(PreflightFragment::javaClass.name) { PreflightFragment.create() }
  fun flight() = FragmentScreen(FlightFragment::javaClass.name) { FlightFragment.create() }
}