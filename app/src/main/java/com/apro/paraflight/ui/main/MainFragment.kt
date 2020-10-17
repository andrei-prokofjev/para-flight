package com.apro.paraflight.ui.main


import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import com.apro.core_ui.BaseFragment
import com.apro.core_ui.onClick
import com.apro.core_ui.toast
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentMainBinding
import com.mapbox.android.core.location.*
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMapOptions
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.expressions.Expression.get
import com.mapbox.mapboxsdk.style.layers.FillExtrusionLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.*
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.mapboxsdk.utils.MapFragmentUtils
import permissions.dispatcher.*
import java.net.URI
import java.net.URISyntaxException


@RuntimePermissions
class MainFragment : BaseFragment() {

  private lateinit var binding: FragmentMainBinding

  private lateinit var mapView: MapView

  private var mapboxMap: MapboxMap? = null

  private var locationEngine: LocationEngine? = null

  private val callback = object : LocationEngineCallback<LocationEngineResult> {
    override fun onSuccess(result: LocationEngineResult) {
      mapboxMap?.locationComponent?.forceLocationUpdate(result.lastLocation)


    }

    override fun onFailure(exception: Exception) {
      toast("failure: $exception")
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    Mapbox.getInstance(requireContext(), getString(R.string.mapbox_access_token))
    binding = FragmentMainBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    with(binding) {
      mapView = MapView(root.context, MapFragmentUtils.resolveArgs(root.context, arguments)).apply {
        onCreate(savedInstanceState)
        mapboxLayout.addView(this)
        getMapAsync {
          mapboxLayout.findViewWithTag<ImageView>("logoView")?.isVisible = false
          mapboxLayout.findViewWithTag<ImageView>("attrView")?.isVisible = false
          mapboxMap = it
          it.setStyle(Style.MAPBOX_STREETS) { style ->


            //   enableLocationComponentWithPermissionCheck(style)

            try {
// Add the marathon route source to the map
// Create a GeoJsonSource and use the Mapbox Datasets API to retrieve the GeoJSON data
// More info about the Datasets API at https://www.mapbox.com/api-documentation/#retrieve-a-dataset
              val courseRouteGeoJson = GeoJsonSource("coursedata", URI("asset://marathon_route.geojson"))

              println(">>> $courseRouteGeoJson")
              style.addSource(courseRouteGeoJson)

// Add FillExtrusion layer to map using GeoJSON data
              style.addLayer(FillExtrusionLayer("course", "coursedata").withProperties(
                fillExtrusionColor(Color.YELLOW),
                fillExtrusionOpacity(0.7f),
                fillExtrusionHeight(get("e"))))
            } catch (exception: URISyntaxException) {
              println(">>> e: $exception")
              //Timber.d(exception)
            }


          }
        }
      }

      menuImageView.onClick { toast("under development") }
      layerImageView.onClick {
        count++
        val n = count % 3

        println(">>> n: $n")

        when (n) {
          0 -> mapboxMap?.setStyle(Style.OUTDOORS)
//          1 -> mapboxMap?.setStyle(Style.TRAFFIC_DAY)
//          2 -> mapboxMap?.setStyle(Style.MAPBOX_STREETS)
          1 -> mapboxMap?.setStyle(Style.SATELLITE)
          2 -> mapboxMap?.setStyle(Style.LIGHT)
//          5 -> mapboxMap?.setStyle(Style.DARK)
//          2 -> mapboxMap?.setStyle(Style.SATELLITE_STREETS)
//          7 -> mapboxMap?.setStyle(Style.TRAFFIC_NIGHT)
        }
      }

      nearMeImageView.onClick { toast("under development") }

      myLocationImageView.onClick {
        val loc = mapboxMap?.locationComponent?.lastKnownLocation
        loc?.let {
          val position = CameraPosition.Builder()
            .target(LatLng(loc.latitude, loc.longitude)) // Sets the new camera position
            .build() // Creates a CameraPosition from the builder
          mapboxMap?.animateCamera(CameraUpdateFactory
            .newCameraPosition(position), 1000)
        }
      }
    }
  }

  var count = 0

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
    mapboxMap?.style?.let { enableLocationComponentWithPermissionCheck(it) }
  }

  override fun onPause() {
    super.onPause()
    mapView.onPause()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  @SuppressLint("MissingPermission")
  @NeedsPermission(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
  )
  fun enableLocationComponent(loadedMapStyle: Style) {
    val locationComponent = mapboxMap?.locationComponent
    val locationComponentActivationOptions =
      LocationComponentActivationOptions.builder(requireContext(), loadedMapStyle)
        .useDefaultLocationEngine(false)
        .build()

    locationComponent?.let {
      it.activateLocationComponent(locationComponentActivationOptions)
      it.isLocationComponentEnabled = true
      it.cameraMode = CameraMode.TRACKING
      it.renderMode = RenderMode.COMPASS

      initLocationEngine()
    }
  }


  @SuppressLint("MissingPermission")
  private fun initLocationEngine() {
    locationEngine = LocationEngineProvider.getBestLocationEngine(requireContext())
    val request = LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
      .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
      .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build()

    locationEngine?.requestLocationUpdates(request, callback, activity?.mainLooper)
    locationEngine?.getLastLocation(callback)
  }

  @SuppressLint("NeedOnRequestPermissionsResult")
  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    onRequestPermissionsResult(requestCode, grantResults)
  }

  @OnPermissionDenied(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
  )
  fun onPermissionDenied() {
    toast("PERMISSIONS DENIED")
  }

  @OnNeverAskAgain(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
  )
  fun onNeverAskAgain() {
    toast("PERMISSIONS REQUERED")
  }

  @OnShowRationale(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
  )
  fun onShowRationale() {
    toast("PERMISSIONS DENIED")
  }

  companion object {
    private const val DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L
    private const val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5

    fun newInstance(options: MapboxMapOptions): MainFragment = MainFragment().apply {
      arguments = MapFragmentUtils.createFragmentArgs(options)
    }
  }
}