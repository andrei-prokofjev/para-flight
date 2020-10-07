package com.apro.core_ui

internal class DoubleClickPreventer(private val skipDurationInMillis: Long) {

  private var lastClickTime: Long = 0

  fun preventDoubleClick(): Boolean {
    val currentTime = System.currentTimeMillis()
    val spentTime = currentTime - lastClickTime
    if (spentTime in 1 until skipDurationInMillis) {
      return true
    }
    lastClickTime = currentTime
    return false
  }
}