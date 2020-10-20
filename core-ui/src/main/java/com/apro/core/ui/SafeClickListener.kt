package com.apro.core.ui

import android.view.View

class SafeClickListener(
  skipDurationInMillis: Long = 300,
  private val listener: (View) -> Unit
) : View.OnClickListener {

  private val doubleClickPreventer: DoubleClickPreventer =
    DoubleClickPreventer(skipDurationInMillis)

  override fun onClick(v: View) {
    if (doubleClickPreventer.preventDoubleClick()) {
      return
    }
    listener.invoke(v)
  }
}