package com.apro.paraflight.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.ui.BaseViewModel
import com.apro.core.util.event.EventBus
import com.apro.paraflight.events.StopFlightEvent
import com.apro.paraflight.mapbox.FlightLocationEngine
import com.apro.paraflight.ui.screen.Screens
import com.github.terrakok.cicerone.Router
import com.mapbox.geojson.Point
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfMeasurement
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FlightScreenViewModel @Inject constructor(
  val appRouter: Router,
  val eventBus: EventBus,
  private val locationEngine: FlightLocationEngine
) : BaseViewModel() {

  private val _locationData = MutableLiveData<Location>()
  val locationData: LiveData<Location> = _locationData

  private val _distData = MutableLiveData(0.0)
  val distData: LiveData<Double> = _distData

  // val dist = TurfMeasurement.distance(route?.get(0), lastPoint, TurfConstants.UNIT_METERS)

  init {
    viewModelScope.launch {
      locationEngine.updateLocationFlow().collect {
        it?.let {
          val prevLocation = _locationData.value ?: it
          val point1 = Point.fromLngLat(prevLocation.longitude, prevLocation.latitude)
          val point2 = Point.fromLngLat(it.longitude, it.latitude)
          val dist = TurfMeasurement.distance(point1, point2, TurfConstants.UNIT_METERS)
          println(">>> dist; $dist ")
          if (dist > 5) {
            _distData.postValue(dist + _distData.value!!)
            _locationData.postValue(it)
          }
        }
      }
    }

  }

  fun land() {
    appRouter.navigateTo(Screens.main())
    eventBus.send(StopFlightEvent())
  }
}