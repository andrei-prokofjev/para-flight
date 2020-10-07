plugins {
  id("com.android.application")
  id("kotlin-android")
  id("kotlin-android-extensions")
}

android {
  compileSdkVersion(29)
  buildToolsVersion("29.0.3")

  defaultConfig {
    applicationId = "com.apro.paraflight"
    minSdkVersion(24)
    targetSdkVersion(29)
    versionCode = 1
    versionName = "1.0"

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

  implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")
  implementation("androidx.core:core-ktx:1.3.2")
  implementation("androidx.appcompat:appcompat:1.2.0")
  implementation("androidx.constraintlayout:constraintlayout:2.0.2")
  testImplementation("junit:junit:4.13")
  androidTestImplementation("androidx.test.ext:junit:1.1.2")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

}