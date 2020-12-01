package com.apro.core.api.di

import com.apro.core.api.*
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class ApiModule {

  @Provides
  @Singleton
  fun providePpgApi(@PpgHttpClient client: OkHttpClient, gson: Gson, @PpgHttpHost host: String): PpgApi =
    PpgApi(createApi(client, gson, host))

  @Provides
  @Singleton
  fun provideWeatherApi(@WeatherHttpClient client: OkHttpClient, gson: Gson, @WeatherHttpHost host: String): WeatherApi =
    WeatherApi(createApi(client, gson, host))

  @Provides
  @Singleton
  fun provideMapboxApi(@MapboxHttpClient client: OkHttpClient, gson: Gson, @MapboxHttpHost host: String): MapboxApi =
    MapboxApi(createApi(client, gson, host))


  private inline fun <reified T> createApi(client: OkHttpClient, gson: Gson, baseUrl: String): T =
    Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(client)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .build()
      .create(T::class.java)
}