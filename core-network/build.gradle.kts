
plugins {
  id("com.android.library")
  id("kotlin-android")
  id("kotlin-android-extensions")
  id("kotlin-kapt")
}


android {
  compileSdkVersion(Versions.compileSdk)
  buildToolsVersion(Versions.buildTools)

  defaultConfig {
    minSdkVersion(Versions.minSdk)
    targetSdkVersion(Versions.targetSdk)
    versionCode = VERSION_CODE
    versionName = VERSION_NAME
    vectorDrawables.useSupportLibrary = true

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
  }
}

dependencies {

  implementation(Libs.kotlin)
  implementation(Libs.ktx)
  implementation(Libs.appCompat)

  implementation(Libs.retrofit)
  implementation(Libs.retrofitGsonConverter)
  implementation(Libs.okHttpLoggingInterceptor)

  implementation(Libs.timber)

  implementation(Libs.dagger)
  kapt(Libs.daggerCompiler)

  testImplementation(TestLibs.junit)
  androidTestImplementation(TestLibs.testJunit)
  androidTestImplementation(TestLibs.espresso)




}