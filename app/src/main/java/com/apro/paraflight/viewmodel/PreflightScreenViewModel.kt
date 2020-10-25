package com.apro.paraflight.viewmodel

import com.apro.core.ui.BaseViewModel
import com.apro.core.util.event.EventBus
import com.apro.paraflight.events.StartFlightEvent
import com.apro.paraflight.ui.screen.Screens
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class PreflightScreenViewModel @Inject constructor(
  val appRouter: Router,
  val eventBus: EventBus
) : BaseViewModel() {

  fun onFlightClick() {
    appRouter.navigateTo(Screens.flight())
    eventBus.send(StartFlightEvent())
  }
}