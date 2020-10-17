package com.apro.paraflight.util

import androidx.annotation.StringRes

interface ResourceProvider {
  fun string(@StringRes id: Int): String
}