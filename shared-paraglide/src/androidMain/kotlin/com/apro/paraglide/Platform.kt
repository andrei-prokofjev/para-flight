package com.apro.paraglide

import android.util.Log

actual object Platform {
  actual val name: String = "Android"
  actual val version: String = "1.0.0"

  actual fun log(message: String) {
    Log.d("HttpClient", message)
  }
}