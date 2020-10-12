plugins {
  id("com.android.application")
  id("kotlin-android")
  id("kotlin-android-extensions")
  id("kotlin-kapt")

}

android {

//  signingConfigs {
//    create("prod") {
//      keyAlias = "releaseKey"
//      keyPassword = project.property("ANDROID_KEY_PASSWORD") as String
//      storeFile = file("../hello.jks")
//      storePassword = project.property("ANDROID_STORE_PASSWORD") as String
//    }
//  }

  compileSdkVersion(Android.compileSdk)
  buildToolsVersion(Android.buildTools)
  ndkVersion = "21.0.6113669"


  defaultConfig {
    applicationId = "com.apro.paraflight"
    minSdkVersion(Android.minSdk)
    targetSdkVersion(Android.targetSdk)
    versionCode = VERSION_CODE
    versionName = VERSION_NAME
    project.ext.set("archivesBaseName", "para-flight-" + defaultConfig.versionName)
    vectorDrawables.useSupportLibrary = true
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = true
//      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
//      isDebuggable = false
//      isJniDebuggable = false
//      signingConfig = signingConfigs.getByName("prod")
//      isRenderscriptDebuggable = false
//      isZipAlignEnabled = true
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
  implementation(project(":core-util"))

  implementation(Libs.kotlin)
  implementation(Libs.ktx)
  implementation(Libs.appCompat)
  implementation(Libs.constraintLayout)

  implementation(Libs.mapbox)
  implementation(Libs.mapboxNavigation)

  implementation(Libs.navigationFragment)
  implementation(Libs.navigationUi)
  implementation(Libs.navigationFeatures)

  implementation(Libs.lifecycleExtensions)
  kapt(Libs.lifecycleCompiler)
  implementation(Libs.lifecycleViewModelKtx)
  implementation(Libs.lifecycleRuntimeKtx)

  implementation(Libs.permissionsDispatcher)
  kapt(Libs.permissionsDispatcherCompiler)

  implementation(Libs.appcenterAnalytics)
  implementation(Libs.appcenterCrashes)

  testImplementation(TestLibs.navigationTest)

  testImplementation(TestLibs.junit)
  androidTestImplementation(TestLibs.testJunit)
  androidTestImplementation(TestLibs.espresso)

}