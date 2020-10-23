package com.apro.core.network.di

import com.apro.core.network.BuildConfig
import com.apro.core.network.api.WeatherApi
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface NetworkComponent {

  fun api(): WeatherApi

}

@Module
abstract class NetworkModule {

  companion object {
    private const val BASE_URL = "https://api.rawg.io/"

    @Provides
    @Singleton
    fun provideApi(): WeatherApi = Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(
        OkHttpClient.Builder()
          .addInterceptor(HttpLoggingInterceptor().apply {
            level =
              if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
              else HttpLoggingInterceptor.Level.NONE
          })
          .build()
      )
      .build()
      .create(WeatherApi::class.java)
  }
}