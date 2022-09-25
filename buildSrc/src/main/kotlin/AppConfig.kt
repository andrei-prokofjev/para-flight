sealed class AppConfig {
  abstract val appId: String

  abstract val majorVersion: Int
  private val minorVersion = System.getenv("MINOR")?.toInt() ?: 0
  private val patchNumber = System.getenv("PATCH_NUMBER")?.toInt() ?: 0
  private val buildNumber = System.getenv("BUILD_NUMBER")?.toInt() ?: 0

  // 1 digit for major, 2 for minor, 2 for patch, 3 for build number
  val versionCode get() = majorVersion * 10000000 + minorVersion * 100000 + patchNumber * 10000 + buildNumber

  // This [versionName] cannot and must not be different than Major.Minor.Patch+Build format
  // Read more: https://semver.org/
  val versionName get() = "${majorVersion}.${minorVersion}.${patchNumber}+${buildNumber}"

  object ParaFlight : AppConfig() {
    override val appId = "com.apro.paraflight"
    override val majorVersion = System.getenv("MAJOR")?.toInt() ?: 6
  }


  companion object {
    const val BUILD_TOOLS = "33.0.0"
    const val COMPILE_SDK = 33
    const val TARGET_SDK = 33
    const val MIN_SDK = 28
  }
}
