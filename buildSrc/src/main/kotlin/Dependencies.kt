object Versions {
  const val KOTLIN = "1.7.0"
  const val GRADLE = "7.2.1"
  const val NAVIGATION_SAFE_ARGS = "2.3.5"
  const val LIFECYCLE = "2.4.0"
  const val COMPOSE = "1.2.0-rc02"
  const val COMPOSE_COMPILER = "1.2.0"
  const val COMPOSE_MATERIAL_3 = "1.0.0-alpha14"
  const val COMPOSE_MATERIAL = "1.2.1"
  const val NAVIGATION = "2.4.1"
  const val ADAPTER_DELEGATES = "4.3.1"
  const val POWERMOCK = "2.0.9"
  const val DAGGER = "2.42"
  const val HILT = "2.42"
  const val RETROFIT = "2.9.0"
  const val ROOM = "2.4.2"
  const val MOCKK = "1.12.1"
  const val ALLURE = "2.3.1"
  const val ESPRESSO = "3.5.0-alpha05"
  const val VPN_FIDES = "1.0.2"
  const val VPN_MODULE = "1.0.2"
  const val KTOR = "2.1.1"
}

object Dependencies {
  const val CORE_KTX = "androidx.core:core-ktx:1.7.0"
  const val APPCOMPAT = "androidx.appcompat:appcompat:1.4.0"
  const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.0.4"
  const val RECYCLERVIEW = "androidx.recyclerview:recyclerview:1.2.0"
  const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:1.3.5"
  const val SPLASHSCREEN = "androidx.core:core-splashscreen:1.0.0-beta02"
  const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:1.4.0"

  const val MATERIAL = "com.google.android.material:material:1.5.0"

  const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.3"

  const val KOTLINX_DATETIME = "org.jetbrains.kotlinx:kotlinx-datetime:0.4.0"

  const val COMPOSE_ACTIVITY = "androidx.activity:activity-compose:1.4.0"
  const val COMPOSE_MATERIAL_3 = "androidx.compose.material3:material3:${Versions.COMPOSE_MATERIAL_3}"
  const val COMPOSE_MATERIAL = "androidx.compose.material:material:${Versions.COMPOSE_MATERIAL}"
  const val COMPOSE_MATERIAL_3_WINDOW = "androidx.compose.material3:material3-window-size-class:${Versions.COMPOSE_MATERIAL_3}"
  const val COMPOSE_TOOLING = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
  const val COMPOSE_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}"
  const val COMPOSE_RUNTIME = "androidx.compose.runtime:runtime:1.2.1"

  const val LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
  const val LIFECYCLE_VIEWMODEL_COMPOSE = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1"
  const val LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
  const val LIFECYCLE_COMPILER = "androidx.lifecycle:lifecycle-compiler:${Versions.LIFECYCLE}"
  const val NAV_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
  const val NAV_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"

  const val DATASTORE_PREFERENCES = "androidx.datastore:datastore-preferences:1.0.0"
  const val WORKS_RUNTIME = "androidx.work:work-runtime:2.7.1"

  const val GOOGLE_SAFETYNET = "com.google.android.gms:play-services-safetynet:18.0.1"
  const val GOOGLE_AUTH = "com.google.android.gms:play-services-auth:20.1.0"

  const val ADAPTER_DELEGATES = "com.hannesdorfmann:adapterdelegates4-kotlin-dsl:${Versions.ADAPTER_DELEGATES}"
  const val ADAPTER_DELEGATES_VIEWBINDING = "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:${Versions.ADAPTER_DELEGATES}"

  const val FASTLANE_SCREENGRAB = "tools.fastlane:screengrab:2.1.1"

  const val DAGGER = "com.google.dagger:dagger:${Versions.DAGGER}"
  const val DAGGER_ANDROID = "com.google.dagger:dagger-android:${Versions.DAGGER}"
  const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:${Versions.DAGGER}"
  const val DAGGER_ANDROID_SUPPORT = "com.google.dagger:dagger-android-support:${Versions.DAGGER}"
  const val DAGGER_ANDROID_PROCESSOR = "com.google.dagger:dagger-android-processor:${Versions.DAGGER}"

  const val HILT = "com.google.dagger:hilt-android:${Versions.HILT}"
  const val HILT_NAVIGATION_COMPOSE = "androidx.hilt:hilt-navigation-compose:1.0.0"
  const val HILT_COMPILER = "com.google.dagger:hilt-compiler:${Versions.HILT}"

  const val APK_PARSER = "net.dongliu:apk-parser:2.6.2"

  const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
  const val RETROFIT_MOCK = "com.squareup.retrofit2:retrofit-mock:${Versions.RETROFIT}"
  const val RETROFIT_JSON = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
  const val RETROFIT_SCALARS = "com.squareup.retrofit2:converter-scalars:${Versions.RETROFIT}"
  const val RETROFIT_RXJAVA = "com.squareup.retrofit2:adapter-rxjava:${Versions.RETROFIT}"

  const val FLIPPER_CORE = "com.facebook.flipper:flipper:0.141.0"
  const val FLIPPER_NOOP = "com.facebook.flipper:flipper-noop:0.141.0"
  const val FLIPPER_NETWORK_PLUGIN = "com.facebook.flipper:flipper-network-plugin:0.141.0"
  const val FLIPPER_LEAK_CANARY_PLUGIN = "com.facebook.flipper:flipper-leakcanary2-plugin:0.141.0"
  const val LEAK_CANARY_ANDROID = "com.squareup.leakcanary:leakcanary-android:2.8.1"
  const val SO_LOADER = "com.facebook.soloader:soloader:0.10.3"
  const val LOGBACK_CLASSIC = "ch.qos.logback:logback-classic:1.3.0-alpha14"

  const val ROOM = "androidx.room:room-runtime:${Versions.ROOM}"
  const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
  const val SQL_CIPHER = "net.zetetic:android-database-sqlcipher:4.5.1"

  const val FIREBASE_BOM = "com.google.firebase:firebase-bom:29.3.1"
  const val FIREBASE_CRASHLYTICS = "com.google.firebase:firebase-crashlytics"
  const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics"
  const val FIREBASE_MESSAGING = "com.google.firebase:firebase-messaging-ktx"

  const val FIDES_CONNECTIVITY = "org.malwarebytes.fides:connectivity:${Versions.VPN_FIDES}"
  const val FIDES_CRYPTO = "org.malwarebytes.fides:wg-crypto:${Versions.VPN_FIDES}"
  const val FIDES_STYX = "org.malwarebytes.fides:styx:${Versions.VPN_FIDES}"
  const val FIDES_GP_BILLING = "org.malwarebytes.fides:gp-billing:${Versions.VPN_FIDES}"
  const val FIDES_MY_ACCOUNT = "org.malwarebytes.fides:my-account:${Versions.VPN_FIDES}"
  const val FIDES_KEYSTONE = "org.malwarebytes.fides:keystone:${Versions.VPN_FIDES}"
  const val FIDES_VOS = "org.malwarebytes.fides:vos:${Versions.VPN_FIDES}"
  const val FIDES_LOGGER = "org.malwarebytes.fides:logger:${Versions.VPN_FIDES}"

  const val VPN_CORE = "org.malwarebytes.vpn:core:${Versions.VPN_MODULE}"
  const val VPN_API = "org.malwarebytes.vpn:api:${Versions.VPN_MODULE}"
}

object TestDependencies {
  const val JUNIT = "junit:junit:4.13.2"
  const val TRUTH = "com.google.truth:truth:1.1.3"
  const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0-RC"
  const val CORE_TESTING = "androidx.arch.core:core-testing:2.1.0"

  const val KTOR_SERIALIZATION = "io.ktor:ktor-serialization-kotlinx-json:${Versions.KTOR}"
  const val KTOR_CONTENT_NEGOTIATION = "io.ktor:ktor-client-content-negotiation:${Versions.KTOR}"
  const val KTOR_KOTLINX_JSON = "io.ktor:ktor-serialization-kotlinx-json:${Versions.KTOR}"

  const val ANDROID_TEST_EXT_JUNIT = "androidx.test.ext:junit:1.1.3"
  const val ANDROID_TEST_CORE = "androidx.test:core:1.4.0"
  const val ANDROID_TEST_ORCHESTRATOR = "androidx.test:orchestrator:1.4.1"
  const val ANDROID_TEST_RUNNER = "androidx.test:runner:1.4.0"
  const val ANDROID_TEST_RULES = "androidx.test:rules:1.4.0"
  const val ANDROID_TEST_UIAUTOMATOR = "androidx.test.uiautomator:uiautomator:2.2.0"
  const val ANDROID_TEST_SERVICES = "androidx.test.services:test-services:1.4.1"


  const val ANDROID_SUPPORT_TEST_RUNNER = "com.android.support.test:runner:1.4.1-alpha03"
  const val ANDROID_SUPPORT_TEST_RULES = "com.android.support.test:rules:1.4.0"

  const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"
  const val ESPRESSO_CONTRIB = "androidx.test.espresso:espresso-contrib:${Versions.ESPRESSO}"
  const val ESPRESSO_INTENTS = "androidx.test.espresso:espresso-intents:${Versions.ESPRESSO}"

  const val POWERMOCK_CORE = "org.powermock:powermock-core:${Versions.POWERMOCK}"
  const val POWERMOCK_MOCKITO = "org.powermock:powermock-api-mockito2:${Versions.POWERMOCK}"
  const val POWERMOCK_JUNIT = "org.powermock:powermock-module-junit4:${Versions.POWERMOCK}"

  const val COMPOSE_UI_TEST_JUNIT = "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}"

  const val MOCKK = "io.mockk:mockk:${Versions.MOCKK}"
  const val MOCKK_JVM = "io.mockk:mockk-agent-jvm:${Versions.MOCKK}"

  const val ALLURE_MODEL = "io.qameta.allure:allure-kotlin-model:${Versions.ALLURE}"
  const val ALLURE_JUNIT = "io.qameta.allure:allure-kotlin-junit4:${Versions.ALLURE}"
  const val ALLURE_COMMONS = "io.qameta.allure:allure-kotlin-commons:${Versions.ALLURE}"
  const val ALLURE_ANDROID = "io.qameta.allure:allure-kotlin-android:${Versions.ALLURE}"

  const val KAKAO = "io.github.kakaocup:kakao:3.0.6"
  const val HAMCREST = "org.hamcrest:hamcrest:2.2"
}

object BuildPlugins {
  const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
  const val GRADLE = "com.android.tools.build:gradle:${Versions.GRADLE}"
  const val NAVIGATION_SAFE_ARGS = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.NAVIGATION_SAFE_ARGS}"
  const val GOOGLE_SERVICES = "com.google.gms:google-services:4.3.10"
  const val FIREBASE = "com.google.firebase:perf-plugin:1.4.1"
  const val CRASHLYTICS = "com.google.firebase:firebase-crashlytics-gradle:2.9.1"
  const val HILT = "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}"
  const val ARTIFACTORY = "org.jfrog.buildinfo:build-info-extractor-gradle:4.28.2"
}