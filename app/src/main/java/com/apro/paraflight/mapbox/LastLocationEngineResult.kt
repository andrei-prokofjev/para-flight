package com.apro.paraflight.mapbox

import com.mapbox.android.core.location.LocationEngineCallback
import com.mapbox.android.core.location.LocationEngineResult
import timber.log.Timber

abstract class LastLocationEngineResult : LocationEngineCallback<LocationEngineResult> {

  override fun onFailure(exception: Exception) {
    Timber.e(exception)
  }
}