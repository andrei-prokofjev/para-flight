package com.apro.paraflight.core

import kotlin.math.pow
import kotlin.math.sqrt

data class WindVector(val x: Float = 0f, val y: Float = 0f, val radius: Float = 0f) {
  fun windSpeed() = sqrt((x.pow(2) + y.pow(2)).toDouble()).toFloat()
}
