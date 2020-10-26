object Versions {
  const val buildTools = "30.0.2"
  const val compileSdk = 29
  const val targetSdk = 29
  const val minSdk = 24

  const val kotlin = "1.4.10"

  const val permissionsDispatcher = "4.7.0"

  const val navigation = "2.3.0"

  const val lifecycle = "2.2.0"

  const val appCenterSdk = "3.3.1"

  const val retrofit = "2.9.0"

  const val dagger = "2.27"

  const val room = "2.2.5"
}

object Libs {
  // Android
  const val appCompat = "androidx.appcompat:appcompat:1.2.0"
  const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
  const val ktx = "androidx.core:core-ktx:1.3.2"
  const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.2"
  const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.8"

  const val material = "com.google.android.material:material:1.1.0"
  const val fragment = "androidx.fragment:fragment-ktx:1.2.5"

  const val timber = "com.jakewharton.timber:timber:4.7.1"

  // Lifecycle
  const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
  const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
  const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
  const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
  const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"

  // Permissions
  const val permissionsDispatcher = "org.permissionsdispatcher:permissionsdispatcher:${Versions.permissionsDispatcher}"
  const val permissionsDispatcherCompiler = "org.permissionsdispatcher:permissionsdispatcher-processor:${Versions.permissionsDispatcher}"

  // Navigation
  const val cicerone = "com.github.terrakok:cicerone:6.1"
  const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
  const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
  //const val navigationFeatures = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigation}"

  // Mapbox
  const val mapbox = "com.mapbox.mapboxsdk:mapbox-android-sdk:9.5.0"
  const val mapboxSurf = "com.mapbox.mapboxsdk:mapbox-sdk-turf:5.5.0"
//  const val mapboxNavigation = "com.mapbox.navigation:ui:1.0.0"

  // App center
  const val appcenterAnalytics = "com.microsoft.appcenter:appcenter-analytics:${Versions.appCenterSdk}"
  const val appcenterCrashes = "com.microsoft.appcenter:appcenter-crashes:${Versions.appCenterSdk}"

  // Retrofit
  const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
  const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
  const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.6.0"

  // Dagger
  const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
  const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

  // Room
  const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
  const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
  const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

  const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.5"

}

object BuildPlugins {
  const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
  const val androidGradle = "com.android.tools.build:gradle:4.1.0"
  const val navigationSafeArgsGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
}

object TestLibs {
  const val junit = "junit:junit:4.13"
  const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
  const val testJunit = "androidx.test.ext:junit:1.1.2"

  const val navigationTesting = "androidx.navigation:navigation-testing:${Versions.navigation}"

  // const val roomTesting = "androidx.room:room-testing:${Versions.room}"
}