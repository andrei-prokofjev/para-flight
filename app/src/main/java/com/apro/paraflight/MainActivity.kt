package com.apro.paraflight

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.apro.paraflight.databinding.ActivityMainBinding
import com.mapbox.mapboxsdk.Mapbox

class MainActivity : AppCompatActivity() {

  private val binding by lazy { ActivityMainBinding.inflate(LayoutInflater.from(this)) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Mapbox.getInstance(this, getString(R.string.mapbox_access_token))
    setContentView(binding.root)

//    binding.mapView.apply {
//      onCreate(savedInstanceState)
//      getMapAsync {
//        it.setStyle(Style.MAPBOX_STREETS) {
//          // Map is set up and the style has loaded. Now you can add data or make other map adjustments
//
//        }
//      }
//    }
  }

}