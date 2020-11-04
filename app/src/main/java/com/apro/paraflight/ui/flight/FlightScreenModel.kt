package com.apro.paraflight.ui.flight

import com.apro.core.model.FlightModel
import com.apro.core.util.Speed
import com.apro.core.util.metersPerSecond
import com.apro.core.util.roundTo
import com.apro.core.util.toTimeFormat
import kotlin.math.roundToInt

data class FlightScreenModel(
  val alt: String,
  val speed: String,
  val dist: String,
  val duration: String)


fun FlightModel.toFlightScreenModel(): FlightScreenModel {
  return with(this) {
    FlightScreenModel(
      alt = alt.roundToInt().toString(),
      speed = speed.metersPerSecond.convertTo(Speed.KilometerPerHour).amount.roundToInt().toString(),
      dist = dist?.div(1000)?.roundTo(1)?.toString() ?: "-", //
      duration = duration?.toTimeFormat() ?: "-:-"
    )
  }
}



