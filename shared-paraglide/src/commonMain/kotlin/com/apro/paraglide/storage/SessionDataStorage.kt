package com.apro.paraglide.storage

import com.apro.paraglide.model.JwtToken

interface SessionDataStorage {

  var userId: String?

  var authToken: JwtToken?

  var refreshToken: JwtToken?

  fun clear()
}