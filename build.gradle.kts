// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
  repositories {
    jcenter()
    google()
  }
  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
    classpath("com.android.tools.build:gradle:4.0.2")
  }
}

allprojects {
  repositories {

    maven(url = "https://api.mapbox.com/downloads/v2/releases/maven").apply {
      authentication {
        create<BasicAuthentication>("basic")
      }
      credentials {
        username = "mapbox"
        password = project.property("MAPBOX_DOWNLOADS_TOKEN") as String? ?: ""
      }
    }

    jcenter()
    google()
  }
}
