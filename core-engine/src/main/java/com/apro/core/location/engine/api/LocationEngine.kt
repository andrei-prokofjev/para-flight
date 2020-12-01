package com.apro.core.location.engine.api

import android.location.Location
import com.apro.core.location.engine.model.DilutionOfPrecision
import kotlinx.coroutines.flow.Flow

interface LocationEngine {
  fun requestLocationUpdates()
  fun updateLocationFlow(): Flow<Location>
  fun removeLocationUpdates()

  suspend fun lastLocation(): Location

  fun dopFlow(): Flow<DilutionOfPrecision?>
  fun clear()
  fun calibrate()


  fun setSeaLevelPressure(p: Float)
}