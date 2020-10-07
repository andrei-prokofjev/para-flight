plugins {
  id("com.android.application")
  id("kotlin-android")
  id("kotlin-android-extensions")
}

android {
  compileSdkVersion(Versions.compileSdk)
  buildToolsVersion(Versions.buildTools)

  defaultConfig {
    applicationId = "com.apro.paraflight"
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

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
  }

  buildFeatures {
    dataBinding = true
    viewBinding = true
  }
  flavorDimensions("default")
}

dependencies {
  implementation(project(":core-ui"))

  implementation(Libs.kotlin)
  implementation(Libs.ktx)
  implementation(Libs.appCompat)
  implementation(Libs.constraintLayout)

  implementation(Libs.mapbox)




  testImplementation(Libs.junit)
  androidTestImplementation(Libs.testJunit)
  androidTestImplementation(Libs.testEspresso)
}