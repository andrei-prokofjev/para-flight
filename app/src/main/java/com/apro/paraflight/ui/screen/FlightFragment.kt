package com.apro.paraflight.ui.screen


import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.core.ui.toast
import com.apro.paraflight.BuildConfig
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentMainBinding
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.common.viewBinding
import com.apro.paraflight.viewmodel.FlightScreenComponent
import com.apro.paraflight.viewmodel.FlightViewModel
import com.mapbox.android.core.location.*
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfMeasurement
import permissions.dispatcher.*


@RuntimePermissions
class FlightFragment : BaseFragment(R.layout.fragment_main), BackButtonListener {

  private val component by lazy { FlightScreenComponent.create() }
  private val binding by viewBinding { FragmentMainBinding.bind(it) }
  private val viewModel by viewModels<FlightViewModel> { component.viewModelFactory() }

  private lateinit var mapView: MapView

  private var mapboxMap: MapboxMap? = null

  private var locationEngine: LocationEngine? = null

  private val routeCoordinates = mutableListOf<Point>()

  private val callback = object : LocationEngineCallback<LocationEngineResult> {
    override fun onSuccess(result: LocationEngineResult) {
      result.lastLocation?.let {
        viewModel.updateLocation(it)


      }
    }

    override fun onFailure(exception: Exception) {
      toast("failure: $exception")
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


    Mapbox.getInstance(requireContext(), getString(R.string.mapbox_access_token))
    with(binding) {
      mapView = MapView(root.context).apply {
        onCreate(savedInstanceState)
        mapboxLayout.addView(this)
        getMapAsync {
          mapboxLayout.findViewWithTag<ImageView>("logoView")?.isVisible = false
          mapboxLayout.findViewWithTag<ImageView>("attrView")?.isVisible = false
          mapboxMap = it
          it.setStyle(viewModel.getStyle(DI.preferencesApi.mapbox().mapStyle)) { style ->
            enableLocationComponentWithPermissionCheck(style)
            style.addSource(GeoJsonSource(ROUTE_SOURCE_ID))
            style.addLayer(LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID).withProperties(
              lineWidth(5f),
              lineColor(Color.BLUE)
            ))
          }
        }
      }

      settingsImageView.onClick {

        viewModel.onSettingsClick()
        // findNavController(this@MainFragment).navigate(R.id.action_main_to_settings)

      }


      shareImageView.onClick { viewModel.onShareClick() }
      layerImageView.onClick { viewModel.onLayerClick() }
      nearMeImageView.onClick { viewModel.onNearMeClick() }

      myLocationImageView.onClick {
        viewModel.locationData.value?.let {
          val position = CameraPosition.Builder()
            .target(LatLng(it.latitude, it.longitude))
            .build()
          mapboxMap?.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000)
        }
      }
      compassImageView.onClick { viewModel.onCompassClick() }

      flightImageView.onClick {
        viewModel.onPreflightClick()
        // findNavController(this@MainFragment).navigate(R.id.action_main_to_preflight)

//        if (locationEngine == null) {
//          flightImageView.setImageResource(R.drawable.ic_flight_land)
//          viewModel.clearRouteStore()
//          routeCoordinates.clear()
//          initLocationEngine()
//        } else {
//          flightImageView.setImageResource(R.drawable.ic_flight_takeoff)
//          locationEngine?.removeLocationUpdates(callback)
//          locationEngine = null
//        }
      }

      viewModel.style.observe { mapboxMap?.setStyle(it) }

      // location observer
      viewModel.locationData.observe {
        mapboxMap?.locationComponent?.forceLocationUpdate(it)
        mapboxMap?.cameraPosition = CameraPosition.Builder().target(LatLng(it)).build()

        val lastPoint = Point.fromLngLat(it.longitude, it.latitude)
        routeCoordinates.add(lastPoint)

        val dist = TurfMeasurement.distance(routeCoordinates[0], lastPoint, TurfConstants.UNIT_METERS)

        mapboxMap?.style?.let { style ->
          val source = style.getSourceAs<GeoJsonSource>(ROUTE_SOURCE_ID)
          source?.setGeoJson(FeatureCollection.fromFeatures(arrayOf(Feature.fromGeometry(LineString.fromLngLats(routeCoordinates)))))
        }
      }

      versionTextView.text = BuildConfig.VERSION_NAME
    }
  }


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
    val locationComponentActivationOptions = LocationComponentActivationOptions
      .builder(requireContext(), loadedMapStyle)
      .useDefaultLocationEngine(false)
      .build()

    locationComponent?.let {
      it.activateLocationComponent(locationComponentActivationOptions)
      it.isLocationComponentEnabled = true
      it.cameraMode = CameraMode.TRACKING
      it.renderMode = RenderMode.COMPASS
    }
  }

  @SuppressLint("MissingPermission")
  private fun initLocationEngine() {
    locationEngine = LocationEngineProvider.getBestLocationEngine(requireContext())
    val request = LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
      .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
      .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME)
      .build()


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
    toast("PERMISSIONS REQUIRED")
  }

  @OnShowRationale(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
  )
  fun onShowRationale() {
    toast("PERMISSIONS DENIED")
  }

  companion object {
    private const val DEFAULT_INTERVAL_IN_MILLISECONDS = 2000L
    private const val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5

    private const val ROUTE_SOURCE_ID = "route-source"
    private const val ROUTE_LAYER_ID = "route-layer"


    fun create(): FlightFragment = FlightFragment()
  }

  override fun onBackPressed(): Boolean {
    println(">>> back$")
    DI.appComponent.appRouter().exit()
    return true
  }
}