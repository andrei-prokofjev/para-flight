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

  implementation(project(":shared-paraglide"))

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

  implementation("com.mapbox.maps:android:10.8.0")
  implementation("com.google.accompanist:accompanist-permissions:0.26.4-beta")
  implementation("com.mapbox.navigation:android:2.7.0")

  testImplementationsPack()
  testUtilImplementationsPack()
  androidTestImplementationsPack()
}

