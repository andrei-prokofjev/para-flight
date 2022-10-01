package com.apro.paraglide

actual object Platform {
  actual val name: String = "IOS"
  actual val version: String = "1.0.0"

  actual fun log(message: String) {
    println(message)
  }
}