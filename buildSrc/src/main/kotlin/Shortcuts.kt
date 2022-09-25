import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

fun DependencyHandler.testImplementationsPack() {
  listOf(
    TestDependencies.JUNIT,
    TestDependencies.TRUTH,
    TestDependencies.COROUTINES_TEST,
    TestDependencies.CORE_TESTING,
    TestDependencies.POWERMOCK_CORE,
    TestDependencies.POWERMOCK_MOCKITO,
    TestDependencies.POWERMOCK_JUNIT,
    TestDependencies.COMPOSE_UI_TEST_JUNIT,
    TestDependencies.MOCKK,
    TestDependencies.MOCKK_JVM
  ).forEach {
    add("testImplementation", it)
  }
}

fun DependencyHandler.testUtilImplementationsPack() {
  listOf(
    TestDependencies.ANDROID_TEST_ORCHESTRATOR
  ).forEach {
    add("androidTestUtil", it)
  }
}

fun DependencyHandler.androidTestImplementationsPack() {
  listOf(
    TestDependencies.JUNIT,
    TestDependencies.TRUTH,
    TestDependencies.CORE_TESTING,
    TestDependencies.ANDROID_TEST_CORE,
    TestDependencies.ANDROID_TEST_EXT_JUNIT,
    TestDependencies.ANDROID_TEST_RUNNER,
    TestDependencies.ANDROID_TEST_RULES,
    TestDependencies.ANDROID_TEST_UIAUTOMATOR,
    TestDependencies.ANDROID_TEST_SERVICES,
    TestDependencies.ANDROID_SUPPORT_TEST_RULES,
    TestDependencies.ANDROID_SUPPORT_TEST_RUNNER,
    TestDependencies.COROUTINES_TEST,
    TestDependencies.ESPRESSO_CORE,
    TestDependencies.ESPRESSO_CONTRIB,
    TestDependencies.ESPRESSO_INTENTS,
    TestDependencies.ALLURE_JUNIT,
    TestDependencies.ALLURE_ANDROID,
    TestDependencies.ALLURE_MODEL,
    TestDependencies.ALLURE_COMMONS,
    TestDependencies.KAKAO,
    TestDependencies.HAMCREST,
    TestDependencies.COMPOSE_UI_TEST_JUNIT,
    TestDependencies.KTOR_SERIALIZATION,
    TestDependencies.KTOR_KOTLINX_JSON,
    TestDependencies.KTOR_CONTENT_NEGOTIATION
  ).forEach {
    add("androidTestImplementation", it)
  }
}

fun DependencyHandler.flipperPack() {
  listOf(
    Dependencies.FLIPPER_CORE,
    Dependencies.FLIPPER_NETWORK_PLUGIN,
    Dependencies.FLIPPER_LEAK_CANARY_PLUGIN,
    Dependencies.LEAK_CANARY_ANDROID,
    Dependencies.SO_LOADER,
    Dependencies.LOGBACK_CLASSIC
  ).forEach {
    add("debugApi", it)
  }
}

fun RepositoryHandler.addArtifactRepositories() {
  google()
  mavenLocal()
  mavenCentral()
  maven(url = "https://artifactory.corp.mb-internal.com/artifactory/bta-android-builds") {
    credentials {
      username = "njusufi"
      password = "AP5HewDsWj4iuv6xm6ych7rxwHRtQbcwdw77hv"
    }
  }
  maven(url = "https://dl.bintray.com/qameta/maven")
  maven(url = "https://dl.bintray.com/populov/maven")
  maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
  maven(url = "https://jitpack.io")
}



