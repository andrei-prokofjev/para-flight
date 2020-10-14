plugins {
  id("com.android.library")
  id("kotlin-android")
  id("kotlin-android-extensions")
}


android {
  compileSdkVersion(Android.compileSdk)
  buildToolsVersion(Android.buildTools)

  defaultConfig {
    minSdkVersion(Android.minSdk)
    targetSdkVersion(Android.targetSdk)
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
}

dependencies {

  implementation(Libs.kotlin)
  implementation(Libs.ktx)
  implementation(Libs.appCompat)

  implementation(Libs.coroutines)
  implementation(Libs.timber)


  testImplementation(TestLibs.junit)
  androidTestImplementation(TestLibs.testJunit)
  androidTestImplementation(TestLibs.espresso)

}