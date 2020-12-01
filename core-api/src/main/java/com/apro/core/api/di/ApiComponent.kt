package com.apro.core.api.di

import com.apro.core.api.MapboxApi
import com.apro.core.api.PpgApi
import com.apro.core.api.WeatherApi

interface ApiComponent {

  fun weatherApi(): WeatherApi

  fun ppgApi(): PpgApi

  fun mapboxApi(): MapboxApi

}