package com.apro.paraflight.ui.base

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel() {
  var navigationState: String? by mutableStateOf(null)
    internal set
  var errorState by mutableStateOf<String?>(null)
    internal set

  protected open val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.e(javaClass.simpleName, throwable.message.orEmpty())
    errorState = throwable.message
  }
}