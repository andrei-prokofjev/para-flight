package com.apro.paraflight.ui


//import com.apro.paraflight.enableLocationComponentWithPermissionCheck
//import com.apro.paraflight.onRequestPermissionsResult
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apro.paraflight.R
import com.apro.paraflight.ui.mapbox.MapboxFragment
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMapOptions


class MainActivity : AppCompatActivity() {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
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
      ft.add(R.id.fragmentContainerLayout, MapboxFragment.newInstance(options))
      ft.commit()
    }
  }
}
