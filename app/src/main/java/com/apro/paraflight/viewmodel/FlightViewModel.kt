package com.apro.paraflight.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.db.api.data.store.RouteStore
import com.apro.core.db.api.di.DatabaseApi
import com.apro.core.db.model.LocationPointModel
import com.apro.core.preferenes.api.MapboxPreferences
import com.apro.core.ui.BaseViewModel
import com.apro.paraflight.ui.screen.Screens
import com.github.terrakok.cicerone.Router
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FlightViewModel @Inject constructor(
  private val mapboxPreferences: MapboxPreferences,
  private val routeStore: RouteStore,
  private val databaseApi: DatabaseApi,
  private val appRouter: Router
) : BaseViewModel() {


  private val _style = MutableLiveData<String>()
  val style: LiveData<String> = _style

  private val _locationData = MutableLiveData<Location>()
  val locationData: LiveData<Location> = _locationData

  fun updateLocation(location: Location) {
    _locationData.postValue(location)
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      locationData.value?.let {
        val point = LocationPointModel(it.time, it.latitude, it.longitude, it.altitude)
        routeStore.insertLocationPoint(point)
      }
    }
  }

  fun onSettingsClick() {
    appRouter.navigateTo(Screens.settings())
  }

  fun onNearMeClick() {
  }

  fun onLayerClick() {
    val nextStyle = (mapboxPreferences.mapStyle.ordinal + 1) % MapboxPreferences.MapStyle.values().size
    val mapStyle = MapboxPreferences.MapStyle.values()[nextStyle]
    mapboxPreferences.mapStyle = mapStyle
    _style.postValue(getStyle(mapStyle))
  }

  fun onCompassClick() {

  }

  fun onShareClick() {

  }

  fun clearRouteStore() {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      locationData.value?.let {
        databaseApi.cleaner().clearAll()
      }
    }
  }

  fun getStyle(mapStyle: MapboxPreferences.MapStyle) =
    when (mapStyle) {
      MapboxPreferences.MapStyle.SATELLITE -> Style.SATELLITE
      MapboxPreferences.MapStyle.MAPBOX_STREETS -> Style.MAPBOX_STREETS
      MapboxPreferences.MapStyle.LIGHT -> Style.LIGHT
    }

  fun onPreflightClick() {
   // appRouter.navigateTo(Screens.preflight())
  }
}