plugins {
  id("com.android.library")
  id("kotlin-android")
  id("kotlin-android-extensions")
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
  implementation(Libs.material)
  implementation(Libs.fragment)

  implementation(Libs.coroutines)
  implementation(Libs.timber)


  implementation(Libs.constraintLayout)

  implementation(Libs.delegates)

  testImplementation(TestLibs.junit)
  androidTestImplementation(TestLibs.testJunit)
  androidTestImplementation(TestLibs.espresso)

}