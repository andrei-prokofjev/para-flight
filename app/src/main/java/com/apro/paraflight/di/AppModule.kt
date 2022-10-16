package com.apro.paraflight.di

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.apro.paraflight.BuildConfig
import com.apro.paraglide.Paraglide
import com.apro.paraglide.ParaglideImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.plugins.logging.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

  companion object {
    @Provides
    @Singleton
    @MainCoroutineScope
    fun providesMainCoroutineScope(): CoroutineScope = MainScope()

    @Provides
    @DispatchersIO
    fun providesCoroutineDispatcherIo(): CoroutineDispatcher = Dispatchers.IO

    @SuppressLint("HardwareIds")
    @Provides
    @SerialNumber
    fun provideSerialNumber(@ApplicationContext appContext: Context): String =
      Settings.Secure.getString(appContext.contentResolver, Settings.Secure.ANDROID_ID)

    @Provides
    fun provideParaglideApi(): Paraglide = ParaglideImpl.Builder()
      .baseUrl("http://10.0.2.2:3001/")
      .logLevel(if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE)
      .build()
  }
}