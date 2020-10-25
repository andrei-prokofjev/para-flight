package com.apro.paraflight.di

import android.content.Context
import com.apro.core.util.event.EventBus
import com.apro.paraflight.App
import com.apro.paraflight.mapbox.FlightLocationEngineImpl
import com.apro.paraflight.util.AndroidResourceProvider
import com.apro.paraflight.util.ResourceProvider
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Cicerone.Companion.create
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Component(modules = [AppModule::class, NavigationModule::class])
@Singleton
interface AppComponent {

  fun context(): Context

  fun eventBus(): EventBus

  fun resources(): ResourceProvider

  fun appRouter(): Router

  fun navigatorHolder(): NavigatorHolder

  fun flightLocationEngine(): FlightLocationEngineImpl

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun appContext(context: Context): Builder

    fun appModule(appModule: AppModule): Builder

    fun build(): AppComponent
  }

  companion object {
    fun create(app: App): AppComponent =
      DaggerAppComponent.builder()
        .appContext(app.applicationContext)
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
  fun provideLocationEngine(): FlightLocationEngineImpl = FlightLocationEngineImpl(app)
}

@Module
class NavigationModule {
  private val cicerone: Cicerone<Router> = create()

  @Provides
  @Singleton
  fun provideRouter(): Router = cicerone.router

  @Provides
  @Singleton
  fun provideNavigatorHolder(): NavigatorHolder = cicerone.getNavigatorHolder()
}
