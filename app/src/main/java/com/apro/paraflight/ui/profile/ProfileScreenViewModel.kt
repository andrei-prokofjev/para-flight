package com.apro.paraflight.ui.profile

import com.apro.core.ui.BaseViewModel
import com.apro.core.util.event.EventBus
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class ProfileScreenViewModel @Inject constructor(
  val appRouter: Router,
  val eventBus: EventBus
) : BaseViewModel()