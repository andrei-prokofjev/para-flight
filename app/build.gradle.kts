plugins {
  id("com.android.application")
  id("kotlin-android")
  id("kotlin-android-extensions")
  id("kotlin-kapt")
}

android {

  signingConfigs {
    create("prod") {
      keyAlias = "key"
      keyPassword = if (project.hasProperty("APRO_KEY_PASSWORD"))
        project.property("APRO_KEY_PASSWORD") as String
      else System.getenv()["APRO_KEY_PASSWORD"]

      storeFile = file("../apro.jks")
      storePassword = if (project.hasProperty("APRO_STORE_PASSWORD"))
        project.property("APRO_STORE_PASSWORD") as String
      else System.getenv()["APRO_STORE_PASSWORD"]

    }
  }

  compileSdkVersion(Versions.compileSdk)
  buildToolsVersion(Versions.buildTools)
//  ndkVersion = "21.0.6113669"


  defaultConfig {
    applicationId = "com.apro.paraflight"
    minSdkVersion(Versions.minSdk)
    targetSdkVersion(Versions.targetSdk)
    versionCode = VERSION_CODE
    versionName = VERSION_NAME
    project.ext.set("archivesBaseName", "para-flight-" + defaultConfig.versionName)
    vectorDrawables.useSupportLibrary = true
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
      isDebuggable = false
      isJniDebuggable = false
      signingConfig = signingConfigs.getByName("prod")
      isRenderscriptDebuggable = false
      isZipAlignEnabled = true
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

  testOptions {
    unitTests.isReturnDefaultValues = true
  }


}

dependencies {
  implementation(project(":core-ui"))
  implementation(project(":core-model"))
  implementation(project(":core-util"))
  implementation(project(":core-preferences"))
  implementation(project(":core-db"))
  implementation(project(":core-navigation"))
  implementation(project(":core-voice-guidance"))
  implementation(project(":core-engine"))
  implementation(project(":core-api"))

  implementation(Libs.kotlin)
  implementation(Libs.ktx)
  implementation(Libs.appCompat)
  implementation(Libs.constraintLayout)

  implementation(Libs.delegates)

  implementation(Libs.playServicesAuth)

  implementation(Libs.mapbox)
  implementation(Libs.mapboxSurf)

  implementation(Libs.navigationFragment)
  implementation(Libs.navigationUi)

  implementation(Libs.lifecycleExtensions)
  kapt(Libs.lifecycleCompiler)
  implementation(Libs.lifecycleViewModelKtx)
  implementation(Libs.lifecycleRuntimeKtx)
  implementation(Libs.lifecycleLiveData)

  implementation(Libs.permissionsDispatcher)
  kapt(Libs.permissionsDispatcherCompiler)

  implementation(Libs.appcenterAnalytics)
  implementation(Libs.appcenterCrashes)

  implementation(Libs.timber)

  implementation(Libs.dagger)
  kapt(Libs.daggerCompiler)

  implementation(Libs.roomRuntime)
  implementation(Libs.roomKtx)

  testImplementation(TestLibs.navigationTesting)

  testImplementation(TestLibs.junit)
  androidTestImplementation(TestLibs.testJunit)
  androidTestImplementation(TestLibs.espresso)

  debugImplementation(Libs.leakCanary)

  implementation(Libs.glide)
  kapt(Libs.glideCompiler)
  implementation(Libs.glideTransformations)


}