package com.apro.core.preferenes.di

import android.app.Application

interface AppProvider {
  fun app(): Application
}