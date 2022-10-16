package com.apro.paraflight.ui.dashboard

import com.apro.paraflight.di.DispatchersIO
import com.apro.paraflight.ui.base.BaseViewModel
import com.apro.paraglide.Paraglide
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
  private val paraglideApi: Paraglide,
  @DispatchersIO val dispatchersIO: CoroutineDispatcher
) : BaseViewModel() {


}