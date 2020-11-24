package com.apro.core.network.di

import com.apro.core.network.BuildConfig
import com.apro.core.network.api.PpgApi
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

  fun ppgApi(): PpgApi

  fun whetherApi(): WeatherApi

}

@Module
abstract class NetworkModule {

  companion object {
    private const val PPG_BASE_URL = "http://3.139.235.222/"

    private const val WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/"
    private const val WEATHER_API_KEY = "e8d866e50e173a70c47d194be00f2ad6"


    @Provides
    @Singleton
    fun providePpgApi(): PpgApi = Retrofit.Builder()
      .baseUrl(PPG_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(
        OkHttpClient.Builder()
          .addInterceptor { chain ->
            val builder = chain.request().newBuilder()
              .addHeader("ppg-internal-api-token", "1PPG9bvazA5dy2CDSPQgJkXK0ESXN4rlLkC0ImUx5v2rCZltP7iVLMR7mOGPf")
            chain.proceed(builder.build())
          }
          .addInterceptor(HttpLoggingInterceptor().apply {
            level =
              if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
              else HttpLoggingInterceptor.Level.NONE
          })
          .build()
      )
      .build()
      .create(PpgApi::class.java)

    @Provides
    @Singleton
    fun provideWhetherApi(): WeatherApi = Retrofit.Builder()
      .baseUrl(WEATHER_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(
        OkHttpClient.Builder()
          .addInterceptor { chain ->
            val url = chain.request().url.newBuilder()
              .addQueryParameter("units", "metric") // todo: pass value from settings
              .addQueryParameter("appId", WEATHER_API_KEY)
              .build()

            chain.proceed(chain.request().newBuilder().url(url).build())
          }
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
