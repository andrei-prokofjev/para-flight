package com.apro.paraflight.ui.register

import androidx.lifecycle.viewModelScope
import com.apro.paraflight.ui.base.BaseViewModel
import com.apro.paraglide.Paraglide
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
  private val paraglide: Paraglide
) : BaseViewModel() {

  fun registerClicked(userName: String) {
    viewModelScope.launch(exceptionHandler) {
      paraglide.api.register(userName)
    }
  }
}