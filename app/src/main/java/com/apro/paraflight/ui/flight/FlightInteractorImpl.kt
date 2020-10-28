package com.apro.paraflight.ui.flight

import com.apro.core.util.event.EventBus
import com.apro.paraflight.mapbox.FlightRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class FlightInteractorImpl @Inject constructor(
  val evenBus: EventBus,
  private val flightRepository: FlightRepository

) : FlightInteractor {

  private var scope: CoroutineScope? = null


//  private val messagesChannel = ConflatedBroadcastChannel<PaginationState<ListItem>>(PaginationState(allLoadedStart = true))

  override fun init() {
    clear()
    scope = CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, e -> Timber.e(e) })

    scope?.launch {
      flightRepository.updateLocationFlow().collect {

      }
    }

  }


  override fun clear() {
    flightRepository.clear()
  }
}