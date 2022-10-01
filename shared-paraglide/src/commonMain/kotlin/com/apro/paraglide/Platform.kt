package com.apro.paraglide

expect object Platform {
  val name: String

  val version: String

  fun log(message: String)
}