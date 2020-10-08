object Android {
  const val buildTools = "30.0.2"
  const val compileSdk = 29
  const val targetSdk = 29
  const val minSdk = 24

  const val kotlin = "1.4.10"

  const val navigation = "2.3.0"
}

object Libs {
  const val appCompat = "androidx.appcompat:appcompat:1.2.0"
  const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Android.kotlin}"
  const val ktx = "androidx.core:core-ktx:1.3.2"
  const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.2"

  const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Android.navigation}"
  const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Android.navigation}"
  const val navigationFeatures =
    "androidx.navigation:navigation-dynamic-features-fragment:${Android.navigation}"

  const val mapbox = "com.mapbox.mapboxsdk:mapbox-android-sdk:9.5.0"
  const val mapboxNavigation = "com.mapbox.navigation:ui:1.0.0"
}

object BuildPlugins {
  const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Android.kotlin}"
  const val androidGradle = "com.android.tools.build:gradle:4.0.2"
  const val navigationSafeArgsGradlePlugin =
    "androidx.navigation:navigation-safe-args-gradle-plugin:${Android.navigation}"
}

object TestLibs {
  const val junit = "junit:junit:4.13"
  const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
  const val testJunit = "androidx.test.ext:junit:1.1.2"

  const val navigationTest = "androidx.navigation:navigation-testing:${Android.navigation}"
}