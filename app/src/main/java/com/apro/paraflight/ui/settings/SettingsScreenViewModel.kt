package com.apro.paraflight.ui.settings

import com.apro.core.ui.BaseViewModel
import com.apro.core.util.event.EventBus
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class SettingsScreenViewModel @Inject constructor(
  val appRouter: Router,
  val eventBus: EventBus
) : BaseViewModel()