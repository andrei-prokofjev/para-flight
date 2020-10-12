package com.apro.paraflight.ui.mapbox


import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.apro.core_ui.BaseFragment
import com.apro.core_ui.onClick
import com.apro.core_ui.toast
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentMapboxBinding
import com.mapbox.android.core.location.*
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMapOptions
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.utils.MapFragmentUtils
import permissions.dispatcher.*

@RuntimePermissions
class MapboxFragment : BaseFragment() {

  private lateinit var binding: FragmentMapboxBinding

  val viewModel by viewModels<MapboxViewModel>()

  private var mapView: MapView? = null

  private var mapboxMap: MapboxMap? = null

  private var locationEngine: LocationEngine? = null

  private val callback = object : LocationEngineCallback<LocationEngineResult> {
    override fun onSuccess(result: LocationEngineResult) {
      mapboxMap?.locationComponent?.forceLocationUpdate(result.lastLocation)
      viewModel.setResult(result)
    }


    override fun onFailure(exception: Exception) {
      toast("failure: $exception")
    }
  }

  override fun onCreateView(inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?): View {
    Mapbox.getInstance(requireContext(), getString(R.string.mapbox_access_token))
    binding = FragmentMapboxBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    with(binding) {

      mapView = MapView(root.context, MapFragmentUtils.resolveArgs(root.context, arguments)).apply {
        onCreate(savedInstanceState)
        mapboxLayout.addView(this)
        getMapAsync {
          mapboxLayout.findViewWithTag<ImageView>("logoView").isVisible = false
          mapboxLayout.findViewWithTag<ImageView>("attrView").isVisible = false
          mapboxMap = it
          it.setStyle(Style.OUTDOORS) {
            enableLocationComponentWithPermissionCheck(it)
          }
        }
      }

      viewModel.speed.observe {
        speedMeterView.amount = it.toString()
      }

      viewModel.altitude.observe {
        altitudeMeterView.amount = it.toString()
      }

      speedMeterView.onClick { viewModel.onSpeedMeterClick() }
      altitudeMeterView.onClick { viewModel.onAltitudeClick() }


    }
  }

  override fun onStart() {
    super.onStart()
    mapView?.onStart()
  }

  override fun onResume() {
    super.onResume()
    mapView?.onResume()
    mapboxMap?.style?.let { enableLocationComponentWithPermissionCheck(it) }
  }

  override fun onPause() {
    super.onPause()
    mapView?.onPause()
  }

  override fun onStop() {
    super.onStop()
    mapView?.onStop()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mapView?.onSaveInstanceState(outState)
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView?.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView?.onLowMemory()
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

  override fun getViewToApplyStatusBarMargin(root: View): Array<View> =
    arrayOf(binding.altitudeMeterView, binding.fuelMeterView, binding.speedMeterView)

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
  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
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

    fun newInstance(options: MapboxMapOptions): MapboxFragment = MapboxFragment().apply {
      arguments = MapFragmentUtils.createFragmentArgs(options)
    }
  }
}