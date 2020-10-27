package com.apro.paraflight.ui.flight

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.navigation.AppRouter
import com.apro.core.ui.BaseViewModel
import com.apro.core.util.event.EventBus
import com.apro.paraflight.mapbox.FlightLocationEngine
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FlightScreenViewModel @Inject constructor(
  val appRouter: AppRouter,
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
        _locationData.postValue(it)
      }
    }
  }
}