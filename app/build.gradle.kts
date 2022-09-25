plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  id("dagger.hilt.android.plugin")
  kotlin("plugin.serialization") version "1.7.10"
}

android {

  compileSdk = AppConfig.COMPILE_SDK
  buildToolsVersion = AppConfig.BUILD_TOOLS

  defaultConfig {
    applicationId = AppConfig.ParaFlight.appId
    minSdk = AppConfig.MIN_SDK
    targetSdk = AppConfig.TARGET_SDK
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER
  }

  kapt {
    correctErrorTypes = true
  }

}

dependencies {

  with(Dependencies) {
    implementation(CORE_KTX)
    implementation(COMPOSE_ACTIVITY)
    implementation(LIFECYCLE_VIEWMODEL_COMPOSE)
    implementation(HILT_NAVIGATION_COMPOSE)
    implementation(HILT)
    kapt(HILT_COMPILER)
    implementation(DATASTORE_PREFERENCES)
    implementation(WORKS_RUNTIME)
    implementation(KOTLINX_DATETIME)
    implementation(COMPOSE_MATERIAL_3)
    implementation(COMPOSE_MATERIAL)
    implementation(COMPOSE_PREVIEW)

    implementation(MATERIAL)
    implementation(COMPOSE_MATERIAL_3_WINDOW)
    implementation(SPLASHSCREEN)
    implementation(COMPOSE_MATERIAL)
    implementation(COMPOSE_RUNTIME)

    debugApi(COMPOSE_TOOLING)
  }

  testImplementationsPack()
  testUtilImplementationsPack()
  androidTestImplementationsPack()
}

//android {
//  compileSdk = AppConfig.COMPILE_SDK
//  buildToolsVersion = AppConfig.BUILD_TOOLS
//
//  defaultConfig {
//    applicationId = AppConfig.Endpoint.appId
//    minSdk = AppConfig.MIN_SDK
//    targetSdk = AppConfig.TARGET_SDK
//    versionCode = AppConfig.Endpoint.versionCode
//    setProperty("archivesBaseName", "${project.name}_v-${defaultConfig.versionName}")
//
//    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    testInstrumentationRunnerArguments += mapOf(
//      "clearPackageData" to "true",
//      "disableAnalytics" to "true",
//      "useTestStorageService" to "true"
//    )
//  }
//
//  val environmentDimension = "environment"
//  flavorDimensions += environmentDimension
//  productFlavors {
//    // Flavor for release builds. Default Nebula environment is PROD.
//    create("prod") {
//      dimension = environmentDimension
//      buildConfigField("String", "NEBULA_URL", "\"https://cloud.malwarebytes.com/\"")
//      manifestPlaceholders["appLink"] = "cloud.malwarebytes.com"
//    }
//    // Flavor to test builds by QA team. Default Nebula environment is STAGING.
//    create("qa") {
//      dimension = environmentDimension
//      applicationIdSuffix = ".qa"
//      buildConfigField("String", "NEBULA_URL", "\"https://nebula-staging.cloud.malwarebytes.com/\"")
//      manifestPlaceholders["appLink"] = "nebula-staging.cloud.malwarebytes.com"
//    }
//    // Flavor to launch builds with mock server. Default Nebula URL points to the local machine where an emulator has been launched.
//    create("localTest") {
//      dimension = environmentDimension
//      applicationIdSuffix = ".localTest"
//      buildConfigField("String", "NEBULA_URL", "\"http://10.0.2.2:3001/\"")
//      manifestPlaceholders["appLink"] = "10.0.2.2:3001"
//    }
//    // Flavor to launch builds from Android Studio during development process. Default Nebula environment is QA.
//    create("dev") {
//      dimension = environmentDimension
//      applicationIdSuffix = ".dev"
//      isDefault = true
//      buildConfigField("String", "NEBULA_URL", "\"https://nebula-retina-mb-qa.eng-dev.mb-internal.com/\"")
//      manifestPlaceholders["appLink"] = "nebula-retina-mb-qa.eng-dev.mb-internal.com"
//    }
//  }
//
//  testOptions {
//    execution = "ANDROIDX_TEST_ORCHESTRATOR"
//    animationsDisabled = true
//  }
//
//  buildFeatures {
//    compose = true
//    viewBinding = true  // we need it as long as we use MB4 design in the "feature-scan" module
//    dataBinding = true // we need it as long as we use MB4 design in the "feature-scan" module
//  }
//
//  composeOptions {
//    kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER
//  }
//
//  kapt {
//    correctErrorTypes = true
//  }
//}
//
//dependencies {
//
//
//  with(Dependencies) {
//    implementation(CORE_KTX)
//    implementation(COMPOSE_ACTIVITY)
//    implementation(LIFECYCLE_VIEWMODEL_COMPOSE)
//    implementation(HILT_NAVIGATION_COMPOSE)
//    implementation(HILT)
//    kapt(HILT_COMPILER)
//    implementation(SPLASHSCREEN)
//    implementation(DATASTORE_PREFERENCES)
//    implementation(WORKS_RUNTIME)
//    implementation(KOTLINX_DATETIME)
//
//    implementation(platform(FIREBASE_BOM))
//    implementation(FIREBASE_CRASHLYTICS)
//    implementation(FIREBASE_ANALYTICS)
//    implementation(FIREBASE_MESSAGING)
//    implementation(KOTLINX_DATETIME)
//  }
//
//  testImplementationsPack()
//  testUtilImplementationsPack()
//  androidTestImplementationsPack()
//}