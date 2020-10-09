package com.apro.paraflight


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMapOptions
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.maps.SupportMapFragment


class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    Mapbox.getInstance(this, getString(R.string.mapbox_access_token))

    if (savedInstanceState == null) {
      val options = MapboxMapOptions.createFromAttributes(this, null)
        .camera(
          CameraPosition.Builder()
            .target(LatLng(59.436962, 24.753574))
            .zoom(10.0)
            .build()
        )

      val mapFragment = SupportMapFragment.newInstance(options).apply {
        getMapAsync {
          it.setStyle(Style.TRAFFIC_DAY)
        }
      }

      val ft = supportFragmentManager.beginTransaction()
      ft.add(R.id.fragmentContainerLayout, mapFragment, "com.mapbox.map")
      ft.commit()
    } else {
      supportFragmentManager.findFragmentByTag("com.mapbox.map")
    }
  }
}