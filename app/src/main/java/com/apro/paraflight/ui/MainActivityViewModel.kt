package com.apro.paraflight.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.apro.paraflight.ui.base.BaseViewModel
import com.apro.paraglide.Paraglide
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
  private val paraglide: Paraglide
) : BaseViewModel() {

  var startDestination by mutableStateOf<NavRoute?>(null)
    private set

  init {
    viewModelScope.launch {
      startDestination = if (paraglide.isSessionValid) NavRoute.Dashboard else NavRoute.Register
    }
  }
}