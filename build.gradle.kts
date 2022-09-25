buildscript {
  repositories {
    addArtifactRepositories()
  }

  dependencies {
    with(BuildPlugins) {
      classpath(GRADLE)
      classpath(KOTLIN)
      classpath(NAVIGATION_SAFE_ARGS)
      classpath(GOOGLE_SERVICES)
      classpath(HILT)
      classpath(ARTIFACTORY)
    }
  }
  configurations.classpath {
    resolutionStrategy {
      cacheDynamicVersionsFor(0, "seconds")
      cacheChangingModulesFor(0, "seconds")
    }
  }
}

allprojects {
  repositories {
    addArtifactRepositories()
    maven(url = "https://api.mapbox.com/downloads/v2/releases/maven") {
      authentication {
        create<BasicAuthentication>("basic")
      }
      credentials {
        username = "mapbox"
        password = "sk.eyJ1IjoiYW5kcmVpLXByb2tvZmpldiIsImEiOiJjbDhoNjQ2NGQwM3Z3M29veThnczR5ZTF5In0.NhukJLxRKDHUA2xLjUApcA"
      }
    }
  }
}

subprojects {
  afterEvaluate {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
      kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
      }
    }

    extensions.configure(com.android.build.gradle.BaseExtension::class.java) {
      defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments += mapOf(
          "clearPackageData" to "true",
          "disableAnalytics" to "true",
          "useTestStorageService" to "true"
        )
      }

      buildTypes {
        getByName("release") {
          isMinifyEnabled = true
          consumerProguardFiles("proguard-rules.pro")
        }
      }

      compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
      }

      packagingOptions {
        //resources.excludes.add("META-INF/*")
      }
    }
  }


}


