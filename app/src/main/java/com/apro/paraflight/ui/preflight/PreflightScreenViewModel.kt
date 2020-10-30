package com.apro.paraflight.ui.preflight

import com.apro.core.navigation.AppRouter
import com.apro.core.ui.BaseViewModel
import com.apro.core.util.event.EventBus
import com.apro.paraflight.ui.Screens
import javax.inject.Inject

class PreflightScreenViewModel @Inject constructor(
  val appRouter: AppRouter,
  val eventBus: EventBus
) : BaseViewModel() {

  fun onFlightClick() {
    appRouter.navigateTo(Screens.flight())
  }
}