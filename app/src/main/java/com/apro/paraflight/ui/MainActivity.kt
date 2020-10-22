package com.apro.paraflight.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.ui.screen.Screens
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMapOptions
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes


class MainActivity : AppCompatActivity() {

  private val navigator: Navigator = AppNavigator(this, R.id.main_container, supportFragmentManager, supportFragmentManager.fragmentFactory)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    AppCenter.start(application, getString(R.string.app_center_access_token), Analytics::class.java, Crashes::class.java)
    setContentView(R.layout.activity_main)


//    if (savedInstanceState == null) {
//
    val options = MapboxMapOptions.createFromAttributes(this, null)
      .camera(
        CameraPosition.Builder()
          .target(LatLng(59.436962, 24.753574))
          .zoom(12.0)
          .build()
      )

    DI.appComponent.appRouter().newRootScreen(Screens.main(options))

  }


  override fun onBackPressed() {
    supportFragmentManager.findFragmentById(R.id.main_container)?.let {
      if (it is BackButtonListener && it.onBackPressed()) return else super.onBackPressed()
    } ?: super.onBackPressed()
  }

  override fun onResume() {
    super.onResume()
    DI.appComponent.navigatorHolder().setNavigator(navigator)
  }

  override fun onPause() {
    DI.appComponent.navigatorHolder().removeNavigator()
    super.onPause()
  }
}
