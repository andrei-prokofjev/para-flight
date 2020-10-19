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

//    maven(url = "https://oss.jfrog.org/artifactory/oss-snapshot-local/")

    maven(url = "https://api.mapbox.com/downloads/v2/releases/maven").apply {
      authentication {
        create<BasicAuthentication>("basic")
      }
      credentials {
        username = "mapbox"
        password = if (project.hasProperty("MAPBOX_DOWNLOADS_TOKEN"))
          project.property("MAPBOX_DOWNLOADS_TOKEN") as String else
          System.getenv()["MAPBOX_DOWNLOADS_TOKEN"]
      }
    }

    jcenter()
    google()
  }
}
