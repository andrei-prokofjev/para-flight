package com.apro.paraflight.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

  private val _errorEvent = MutableSharedFlow<Throwable>()
  val errorEvent = _errorEvent.asSharedFlow()

  protected open val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.e(javaClass.simpleName, throwable.message.orEmpty())
    viewModelScope.launch { _errorEvent.emit(throwable) }
  }
}