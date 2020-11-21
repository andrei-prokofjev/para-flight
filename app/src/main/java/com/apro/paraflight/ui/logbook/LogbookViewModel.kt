package com.apro.paraflight.ui.logbook

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apro.core.location.engine.impl.ReplayLocationEngine
import com.apro.core.navigation.AppRouter
import com.apro.core.ui.BaseViewModel
import com.apro.core.ui.adapter.ListItem
import com.apro.paraflight.ui.Screens
import com.apro.paraflight.ui.mapbox.MapboxInteractor
import com.apro.paraflight.ui.mapbox.MapboxSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogbookViewModel @Inject constructor(
  mapboxInteractor: MapboxInteractor,
  private val logbookInteractor: LogbookInteractor,
  val appRouter: AppRouter

) : BaseViewModel() {

  private val _logbooksData = MutableLiveData<List<ListItem>>()
  val logbookData: LiveData<List<ListItem>> = _logbooksData

  init {
    mapboxInteractor.mapboxSettings = MapboxSettings.ReplayFlightMapboxSettings

    logbookInteractor.getLogbooks()

    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      logbookInteractor.logbooksFlow().collect {
        _logbooksData.postValue(it)
      }
    }
  }

  override fun onCleared() {
    logbookInteractor.clear()
  }

  fun startFlightReplay(flightPoints: List<Location>) {
    appRouter.navigateTo(Screens.flight(ReplayLocationEngine(flightPoints), MapboxSettings.ReplayFlightMapboxSettings))
  }


}