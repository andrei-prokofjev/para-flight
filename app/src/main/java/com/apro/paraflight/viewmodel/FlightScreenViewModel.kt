package com.apro.paraflight.viewmodel

import com.apro.core.ui.BaseViewModel
import com.apro.core.util.event.EventBus
import com.apro.paraflight.events.StopFlightEvent
import com.apro.paraflight.ui.screen.Screens
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class FlightScreenViewModel @Inject constructor(
  val appRouter: Router,
  val eventBus: EventBus
) : BaseViewModel() {

  fun land() {
    appRouter.navigateTo(Screens.main())
    eventBus.send(StopFlightEvent())
  }
}