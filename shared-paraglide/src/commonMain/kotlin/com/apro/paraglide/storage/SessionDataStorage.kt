package com.apro.paraglide.storage

import com.apro.paraglide.model.JwtToken

interface SessionDataStorage {

  var userId: String?

  var authToken: String?

  var refreshToken: JwtToken?

  fun clear()
}