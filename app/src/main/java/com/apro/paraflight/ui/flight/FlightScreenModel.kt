package com.apro.paraflight.ui.flight

import android.text.format.DateUtils
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
      speed = speed.roundToInt().toString(),
      dist = dist.roundToInt().toString(),
      duration = duration.toSimpleFormat()
    )
  }
}

private fun Long.toSimpleFormat(): String {
  val minutes = this / DateUtils.MINUTE_IN_MILLIS % 60
  val hours = this / DateUtils.HOUR_IN_MILLIS
  return String.format("%s:%s", hours, if (minutes < 10) "0$minutes" else minutes)
}

