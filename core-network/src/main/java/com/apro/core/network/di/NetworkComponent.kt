package com.apro.core.network.di

import com.apro.core.network.BuildConfig
import com.apro.core.network.api.PpgApi
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

  fun api(): PpgApi

}

@Module
abstract class NetworkModule {

  companion object {
    private const val BASE_URL = "http://10.0.2.2:8080"

    @Provides
    @Singleton
    fun provideApi(): PpgApi = Retrofit.Builder()
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
      .create(PpgApi::class.java)
  }
}