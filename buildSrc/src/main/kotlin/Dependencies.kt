object Versions {
  const val buildTools = "30.0.2"
  const val compileSdk = 29
  const val targetSdk = 29
  const val minSdk = 24

  const val kotlin = "1.4.10"

  //  const val dokka = "1.4.0-rc"
//
//  const val smack = "4.3.4"
//  const val retrofit = "2.9.0"
//  const val stetho = "1.5.1"
//  const val glide = "4.11.0"
//  const val dagger = "2.28"
//  const val lifecycle = "2.2.0"
//  const val permissionsDispatcher = "4.7.0"
//  const val room = "2.2.5"
}

object Libs {
  const val appCompat = "androidx.appcompat:appcompat:1.2.0"
  const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
  const val ktx = "androidx.core:core-ktx:1.3.2"
  const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.2"
  const val junit = "junit:junit:4.13"
  const val testJunit = "androidx.test.ext:junit:1.1.2"
  const val testEspresso = "androidx.test.espresso:espresso-core:3.3.0"


  const val mapbox = "com.mapbox.mapboxsdk:mapbox-android-sdk:9.5.0"
  const val navigationUi = "com.mapbox.navigation:ui:1.0.0"


  //  const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.8"
//
//  const val androidxAnnotation = "androidx.annotation:annotation:1.1.0"
//
//  const val timber = "com.jakewharton.timber:timber:4.7.1"
//  const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.3"
//  const val amplitude = "com.amplitude:android-sdk:2.26.0"
//
//  const val tapjoy = "com.tapjoy:tapjoy-android-sdk:12.4.2"
//
//  const val fragment = "androidx.fragment:fragment-ktx:1.2.4"
//  const val browser = "androidx.browser:browser:1.2.0"

//  const val material = "com.google.android.material:material:1.1.0"

//  const val facebook = "com.facebook.android:facebook-android-sdk:7.0.0"
//  const val scaleImageView = "com.davemorrissey.labs:subsampling-scale-image-view:3.10.0"
//  const val multidex = "androidx.multidex:multidex:2.0.1"
//  const val exoPlayer = "com.google.android.exoplayer:exoplayer:2.11.5"
//  const val mp4parser = "com.googlecode.mp4parser:isoparser:1.1.22"
//  const val libphonenumber = "com.googlecode.libphonenumber:libphonenumber:8.12.5"
//  const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
//  const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
//  const val lottie = "com.airbnb.android:lottie:3.4.1"
//  const val delegates = "com.hannesdorfmann:adapterdelegates4:4.3.0"
//  const val viewPager = "androidx.viewpager2:viewpager2:1.0.0"
//  const val cameraView = "com.otaliastudios:cameraview:2.6.2"
//  const val workManager = "androidx.work:work-runtime-ktx:2.3.4"
//
//  //Lifecycle
//  const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
//  const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
//  const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
//  const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
//
//  //Permissions
//  const val permissionsDispatcher = "org.permissionsdispatcher:permissionsdispatcher:${Versions.permissionsDispatcher}"
//  const val permissionsDispatcherCompiler = "org.permissionsdispatcher:permissionsdispatcher-processor:${Versions.permissionsDispatcher}"
//
//  //Firebase
//  const val firebaseCore = "com.google.firebase:firebase-core:17.4.3"
//  const val firebaseMessaging = "com.google.firebase:firebase-messaging:20.2.0"
//  const val firebaseDynamicLinks = "com.google.firebase:firebase-dynamic-links:19.1.0"
//  const val firebaseAnalytics = "com.google.firebase:firebase-analytics:17.4.3"
//
//  //Chat
//  const val smackExtensions = "org.igniterealtime.smack:smack-android-extensions:${Versions.smack}"
//  const val smackTcp = "org.igniterealtime.smack:smack-tcp:${Versions.smack}"
//
//  //Rx
//  const val rxAndroid2 = "io.reactivex.rxjava2:rxandroid:2.1.1"
//  const val rxJava2 = "io.reactivex.rxjava2:rxjava:2.2.19"
//  const val rxRelay = "com.jakewharton.rxrelay2:rxrelay:2.1.1"
//
//  //Network
//  const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
//  const val retrofit2ConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
//  const val retrofit2RxAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
//  const val threeten = "com.jakewharton.threetenabp:threetenabp:1.2.4"
//  const val okhttp3Logging = "com.squareup.okhttp3:logging-interceptor:4.7.2"
//  const val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
//  const val stethoOkHttp = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"
//  const val gson = "com.google.code.gson:gson:2.8.6"
//
//  //Glide
//  const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
//  const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
//  const val glideTransformations = "jp.wasabeef:glide-transformations:4.1.0"
//
//  const val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.10.1"
//
//  const val javaxInject = "javax.inject:javax.inject:1"
//
//  const val ffmpeg = "com.arthenica:mobile-ffmpeg-min:4.3.2"
//
//  // Spyglass - mention lib
//  const val spyglass = "com.linkedin.android.spyglass:spyglass:2.2.0"
//
//  // Database
//  const val room = "androidx.room:room-runtime:${Versions.room}"
//  const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
//  const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
}