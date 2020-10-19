package com.apro.paraflight


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apro.paraflight.ui.screen.main.MainFragment
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMapOptions
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes


class MainActivity : AppCompatActivity() {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    AppCenter.start(application, getString(R.string.app_center_access_token), Analytics::class.java, Crashes::class.java)

    setContentView(R.layout.activity_main)

    if (savedInstanceState == null) {

      val options = MapboxMapOptions.createFromAttributes(this, null)
        .camera(
          CameraPosition.Builder()
            .target(LatLng(59.436962, 24.753574))
            .zoom(12.0)
            .build()
        )

      val ft = supportFragmentManager.beginTransaction()
      ft.add(R.id.fragmentContainerLayout, MainFragment.newInstance(options))
      ft.commit()
    }
  }
}
