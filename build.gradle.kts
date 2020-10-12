// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
  val kotlin_version by extra("1.4.10")
  repositories {
    jcenter()
    google()
  }
  dependencies {
    classpath(BuildPlugins.kotlinGradlePlugin)
    classpath(BuildPlugins.androidGradle)
    classpath(BuildPlugins.navigationSafeArgsGradlePlugin)
    "classpath"("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
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
        //password = project.property("MAPBOX_DOWNLOADS_TOKEN") as String? ?: ""
        password =
          "sk.eyJ1IjoiYW5kcmVpLXByb2tvZmpldiIsImEiOiJja2Z6YzRod2MwMXFrMnpxcXdwcjNsOTk5In0.MlS2v7VgK7RTpi7VuPDNTw"
      }
    }

    jcenter()
    google()
  }
}
