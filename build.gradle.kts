// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
  repositories {
    jcenter()
    google()
  }
  dependencies {
    classpath(BuildPlugins.kotlinGradlePlugin)
    classpath(BuildPlugins.androidGradle)
    classpath(BuildPlugins.navigationSafeArgsGradlePlugin)
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
        password =
          "sk.eyJ1IjoiYW5kcmVpLXByb2tvZmpldiIsImEiOiJja2Z6YzRod2MwMXFrMnpxcXdwcjNsOTk5In0.MlS2v7VgK7RTpi7VuPDNTw"
//        try {
//
//          password = project.property("MAPBOX_DOWNLOADS_TOKEN") as String? ?: ""
//        } catch (e: kotlin.Exception) {
//        }
      }
    }

    jcenter()
    google()
  }
}
