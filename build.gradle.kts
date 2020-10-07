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
    jcenter()
    google()
  }
}
