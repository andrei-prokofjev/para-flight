package com.apro.paraflight.ui.about

import com.apro.core.navigation.AppRouter
import com.apro.core.ui.BaseViewModel
import com.apro.core.util.event.EventBus
import javax.inject.Inject

class AboutScreenViewModel @Inject constructor(
  val appRouter: AppRouter,
  val eventBus: EventBus
) : BaseViewModel() {

  fun onBackClick() {
    appRouter.exit()
  }

}