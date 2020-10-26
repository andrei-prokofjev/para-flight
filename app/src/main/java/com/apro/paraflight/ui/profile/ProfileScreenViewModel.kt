package com.apro.paraflight.ui.profile

import com.apro.core.navigation.AppRouter
import com.apro.core.ui.BaseViewModel
import com.apro.core.util.event.EventBus
import javax.inject.Inject

class ProfileScreenViewModel @Inject constructor(
  val appRouter: AppRouter,
  val eventBus: EventBus
) : BaseViewModel()