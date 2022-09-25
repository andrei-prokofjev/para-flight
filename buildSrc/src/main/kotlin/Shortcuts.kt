import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler

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

fun RepositoryHandler.addArtifactRepositories() {
  google()
  mavenLocal()
  mavenCentral()
}



