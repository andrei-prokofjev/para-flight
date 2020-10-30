package com.apro.paraflight.util

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface ResourceProvider {
  fun getString(@StringRes id: Int): String

  fun getString(@StringRes id: Int, vararg args: Any?): String

  fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg args: Any?): String

  fun getDrawable(@DrawableRes id: Int): Drawable?
}