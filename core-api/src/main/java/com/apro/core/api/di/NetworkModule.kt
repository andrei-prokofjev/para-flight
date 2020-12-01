package com.apro.core.api.di

import com.apro.core.api.*
import com.apro.core.preferenes.api.SettingsPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

  @Provides
  @PpgHttpHost
  fun providePpgHttpHost(): String = "http://3.139.235.222/"

  @Provides
  @WeatherHttpHost
  fun provideWeatherHttpHost(): String = "http://api.openweathermap.org/data/2.5/"

  @Provides
  @MapboxHttpHost
  fun provideMapboxHttpHost(): String = "https://api.mapbox.com/v4/"


  @Provides
  @Singleton
  @PpgHttpClient
  fun providePpgOkHttpClient(
//      deviceInfo: DeviceInfo,
//      eventBus: EventBus,
    // preferencesApi: PreferencesApi,

    gson: Gson
  ): OkHttpClient {

    return OkHttpClient.Builder()
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
      .readTimeout(10_000L, TimeUnit.MILLISECONDS)
      .build()
  }

  @Provides
  @Singleton
  @WeatherHttpClient
  fun provideWhetherOkHttpClient(
    settingsPreferences: SettingsPreferences
  ): OkHttpClient {

    return OkHttpClient.Builder()
      .addInterceptor { chain ->
        val url = chain.request().url.newBuilder()
          .addQueryParameter("units", settingsPreferences.units.name)
          .addQueryParameter("appId", WEATHER_API_KEY) // todo:
          .build()

        chain.proceed(chain.request().newBuilder().url(url).build())
      }
      .addInterceptor(HttpLoggingInterceptor().apply {
        level =
          if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
          else HttpLoggingInterceptor.Level.NONE
      })
      .build()
  }

  @Provides
  @Singleton
  @MapboxHttpClient
  fun provideMapboxOkHttpClient(): OkHttpClient {

    return OkHttpClient.Builder()

      .addInterceptor { chain ->
        val url = chain.request().url.newBuilder()
          .addQueryParameter("access_token", MAPBOX_ACCESS_TOKEN)
          .build()

        chain.proceed(chain.request().newBuilder().url(url).build())
      }
      .addInterceptor(HttpLoggingInterceptor().apply {
        level =
          if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
          else HttpLoggingInterceptor.Level.NONE
      })
      .build()
  }


  @Provides
  @Singleton
  fun provideGson(): Gson {
    return GsonBuilder()
      //.registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
//        .registerTypeAdapter(BaseContentFeedItemModel::class.java, FeedItemAdapter())
//        .registerTypeAdapter(FeedItemResponseDto::class.java, FeedItemResponseAdapter())
//        .registerTypeAdapter(ContentFeedItemResponseDto::class.java, ContentFeedItemResponseAdapter())
//        .registerTypeAdapter(BaseContentModel::class.java, OldContentAdapter())
//        .registerTypeAdapter(BaseCaptionChunk::class.java, LocalCaptionChunkAdapter())
//        .registerTypeAdapter(AbstractCaptionChunkDto::class.java, CaptionChunkAdapter())
//        .registerTypeAdapter(AbstractContentWrapperResponseDto::class.java, ContentWrapperAdapter())
      .create()
  }

  companion object {
    private const val WEATHER_API_KEY = "e8d866e50e173a70c47d194be00f2ad6"
    private const val MAPBOX_ACCESS_TOKEN = "pk.eyJ1IjoiYW5kcmVpLXByb2tvZmpldiIsImEiOiJja2Z5Y3hxcjMxdDJxMnd0OHFtbGFsbG1kIn0.QlXEa2eS89Z3FpwDG7EPWA"
  }
}

