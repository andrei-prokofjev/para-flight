package com.apro.paraflight


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMapOptions
import com.mapbox.mapboxsdk.maps.Style


class MapboxFragment : Fragment() {

  lateinit var mapView: MapView


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Mapbox.getInstance(requireContext(), getString(R.string.mapbox_access_token))
    val options = MapboxMapOptions.createFromAttributes(requireContext(), null)
      .camera(
        CameraPosition.Builder()
          .target(LatLng(59.436962, 24.753574))
          .zoom(10.0)
          .build()
      )


    mapView = MapView(requireContext(), options).apply {
      onCreate(savedInstanceState)
      getMapAsync {
        it.setStyle(Style.TRAFFIC_NIGHT)

      }
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = mapView

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
  }

  override fun onPause() {
    super.onPause()
    mapView.onPause()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }

  companion object {
    fun create() = MapboxFragment()
  }
}