package com.apro.paraflight.di

import com.apro.core.navigation.AppRouter
import com.apro.core.navigation.di.NavigationModule
import com.apro.core.util.event.EventBus
import com.apro.paraflight.App

import com.apro.paraflight.mapbox.FlightLocationRepositoryImpl
import com.apro.paraflight.util.AndroidResourceProvider
import com.apro.paraflight.util.ResourceProvider
import com.github.terrakok.cicerone.NavigatorHolder
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Component(modules = [AppModule::class, NavigationModule::class])
@Singleton
interface AppComponent {

  fun eventBus(): EventBus

  fun resources(): ResourceProvider

  fun appRouter(): AppRouter

  fun navigatorHolder(): NavigatorHolder

  fun flightLocationEngine(): FlightLocationRepositoryImpl

  companion object {
    fun create(app: App): AppComponent =
      DaggerAppComponent.builder()
        .appModule(AppModule(app))
        .build()
  }
}

@Module
class AppModule(val app: App) {
  @Provides
  @Singleton
  fun resourceProvider(): ResourceProvider = AndroidResourceProvider(app)

  @Provides
  @Singleton
  fun eventBusProvider(): EventBus = EventBus

  @Provides
  @Singleton
  fun provideLocationEngine(): FlightLocationRepositoryImpl = FlightLocationRepositoryImpl(app)
}


