package com.apro.paraflight.ui.register

import androidx.lifecycle.viewModelScope
import com.apro.paraflight.di.DispatchersIO
import com.apro.paraflight.ui.NavRoute
import com.apro.paraflight.ui.base.BaseViewModel
import com.apro.paraglide.Paraglide
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.plugins.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
  private val paraglide: Paraglide,
  @DispatchersIO val dispatchersIO: CoroutineDispatcher
) : BaseViewModel() {
  fun registerClicked(userName: String, password: String) {
    viewModelScope.launch(dispatchersIO + exceptionHandler) {
      try {
        paraglide.api.register(userName, password)
        navigationState = NavRoute.Dashboard.route
      } catch (t: Throwable) {
        when (t) {
          is ClientRequestException -> errorState = t.response.status.toString()
          else -> throw t
        }
      }
    }
  }
}