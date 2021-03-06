package com.apro.core.api

import com.apro.controller.WeatherController
import com.apro.core.model.weather.CoordModel

class WeatherApi(private val controller: WeatherController) {

  suspend fun getWeatherByLocation(location: CoordModel) = controller.weatherByLocation(location.latitude, location.longitude).model()

  suspend fun getWeatherByCity(city: String) = controller.weatherByCity(city).model()

}
