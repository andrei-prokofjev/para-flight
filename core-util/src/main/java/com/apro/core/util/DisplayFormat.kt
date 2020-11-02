package com.apro.core.util

import android.text.format.DateUtils
import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.roundTo(numFractionDigits: Int): Double {
  val factor = 10.0.pow(numFractionDigits.toDouble())
  return (this * factor).roundToInt() / factor
}

fun Float.roundTo(numFractionDigits: Int): Float {
  val factor = 10.0.pow(numFractionDigits.toDouble()).toFloat()
  return (this * factor).roundToInt() / factor
}

fun Long.toTimeFormat(): String {
  val minutes = this / DateUtils.MINUTE_IN_MILLIS % 60
  val hours = this / DateUtils.HOUR_IN_MILLIS
  return String.format("%s:%s", hours, if (minutes < 10) "0$minutes" else minutes)
}