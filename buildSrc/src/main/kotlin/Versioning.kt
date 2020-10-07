private const val MAJOR = 1
private const val MINOR = 0

private val BUILD_NUMBER = System.getenv()["BUILD_NUMBER"]

val VERSION_CODE = BUILD_NUMBER?.toInt() ?: 1
val VERSION_NAME = "$MAJOR.$MINOR.$VERSION_CODE"
