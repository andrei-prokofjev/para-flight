package com.apro.core.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

  open val exceptionHandler = CoroutineExceptionHandler { _, e -> Timber.e(">>> $e") }
}