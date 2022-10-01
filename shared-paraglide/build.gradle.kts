plugins {
  kotlin("multiplatform")
  kotlin("native.cocoapods")
  id("com.android.library")
  kotlin("plugin.serialization") version "1.7.10"
}

version = "1.0"

val ktorVersion = "2.1.1"

kotlin {
  android()
  iosX64()
  iosArm64()
  iosSimulatorArm64()

  cocoapods {
    summary = "Some description for the Shared Module"
    homepage = "Link to the Shared Module homepage"
    version = "1.0"
    ios.deploymentTarget = "14.1"
    framework {
      baseName = "shared-paraglide"
    }
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation("io.ktor:ktor-client-core:$ktorVersion")
        api("io.ktor:ktor-client-logging:$ktorVersion")
        implementation("io.ktor:ktor-client-auth:$ktorVersion")
        implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
        implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
        implementation("com.russhwolf:multiplatform-settings-no-arg:1.0.0-alpha01")
      }
    }

    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }
    val androidMain by getting {
      dependencies {
        implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
      }
    }
    val androidTest by getting
    val iosX64Main by getting
    val iosArm64Main by getting
    val iosSimulatorArm64Main by getting
    val iosMain by creating {
      dependsOn(commonMain)
      iosX64Main.dependsOn(this)
      iosArm64Main.dependsOn(this)
      iosSimulatorArm64Main.dependsOn(this)
    }
    val iosX64Test by getting
    val iosArm64Test by getting
    val iosSimulatorArm64Test by getting
    val iosTest by creating {
      dependsOn(commonTest)
      iosX64Test.dependsOn(this)
      iosArm64Test.dependsOn(this)
      iosSimulatorArm64Test.dependsOn(this)
    }
  }
}

android {
  namespace = "com.apro.paraglide"
  compileSdk = 33
  defaultConfig {
    minSdk = 29
    targetSdk = 33
  }
}