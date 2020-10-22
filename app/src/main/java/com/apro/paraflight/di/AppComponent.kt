package com.apro.paraflight.di

import android.content.Context
import com.apro.paraflight.util.AndroidResourceProvider
import com.apro.paraflight.util.ResourceProvider
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Cicerone.Companion.create
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.*
import javax.inject.Singleton

@Component(modules = [AppModule::class, NavigationModule::class])
@Singleton
interface AppComponent {

  fun resources(): ResourceProvider

  fun appRouter(): Router

  fun navigatorHolder(): NavigatorHolder


  @Component.Builder
  interface Builder {
    @BindsInstance
    fun appContext(context: Context): Builder

    fun build(): AppComponent
  }
}

@Module
abstract class AppModule {
  @Binds
  @Singleton
  abstract fun bindResourceProvider(provider: AndroidResourceProvider): ResourceProvider
}

@Module
class NavigationModule {
  private val cicerone: Cicerone<Router> = create()

  @Provides
  @Singleton
  fun provideRouter(): Router {
    return cicerone.router
  }

  @Provides
  @Singleton
  fun provideNavigatorHolder(): NavigatorHolder {
    return cicerone.getNavigatorHolder()
  }
}
