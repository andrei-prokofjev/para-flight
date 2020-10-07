package com.apro.paraflight

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.apro.paraflight.databinding.ActivityMainBinding
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.Style

class MainActivity : AppCompatActivity() {

  private val binding by lazy { ActivityMainBinding.inflate(LayoutInflater.from(this)) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Mapbox.getInstance(this, getString(R.string.mapbox_access_token))
    setContentView(binding.root)

    binding.mapView.apply {
      onCreate(savedInstanceState)
      getMapAsync {
        it.setStyle(Style.MAPBOX_STREETS) {
          // Map is set up and the style has loaded. Now you can add data or make other map adjustments
          // MAPBOX_DOWNLOADS_TOKEN=sk.eyJ1IjoiYW5kcmVpLXByb2tvZmpldiIsImEiOiJja2Z6YzRod2MwMXFrMnpxcXdwcjNsOTk5In0.MlS2v7VgK7RTpi7VuPDNTw

        }
      }
    }
  }

  override fun onStart() {
    super.onStart()
    binding.mapView.onStart()
  }

  override fun onResume() {
    super.onResume()
    binding.mapView.onResume()
  }

  override fun onPause() {
    super.onPause()
    binding.mapView.onPause()
  }

  override fun onStop() {
    super.onStop()
    binding.mapView.onStop()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    binding.mapView.onSaveInstanceState(outState)
  }

  override fun onLowMemory() {
    super.onLowMemory()
    binding.mapView.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    binding.mapView.onDestroy()
  }
}