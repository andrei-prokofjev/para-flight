package com.apro.paraflight.preferences.di

import android.app.Application

interface AppProvider {
  fun app(): Application
}