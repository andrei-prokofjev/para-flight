package com.apro.paraflight


import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.apro.core_ui.toast
import com.mapbox.android.core.location.*
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMapOptions
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.maps.SupportMapFragment
import permissions.dispatcher.*
import java.lang.ref.WeakReference


@RuntimePermissions
class MainActivity : AppCompatActivity() {

  var mapboxMap: MapboxMap? = null

  private var locationEngine: LocationEngine? = null

  private val callback = LocationChangeListener(this)


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    Mapbox.getInstance(this, getString(R.string.mapbox_access_token))

    if (savedInstanceState == null) {
      val options = MapboxMapOptions.createFromAttributes(this, null)
        .camera(
          CameraPosition.Builder()
            .target(LatLng(59.436962, 24.753574))
            .zoom(12.0)
            .build()
        )

      val mapFragment = SupportMapFragment.newInstance(options).apply {
        getMapAsync {
          this@MainActivity.mapboxMap = it
          view?.findViewWithTag<View>("logoView")?.isVisible = false
          view?.findViewWithTag<View>("attrView")?.isVisible = false
          it.setStyle(Style.TRAFFIC_DAY) {

            enableLocationComponentWithPermissionCheck(it)
          }
        }
      }

      val ft = supportFragmentManager.beginTransaction()
      ft.add(R.id.fragmentContainerLayout, mapFragment, "com.mapbox.map")
      ft.commit()
    } else {
      supportFragmentManager.findFragmentByTag("com.mapbox.map")
    }
  }

  override fun onResume() {
    super.onResume()
    mapboxMap?.style?.let { enableLocationComponentWithPermissionCheck(it) }
  }


  @SuppressLint("MissingPermission")
  @NeedsPermission(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
  )
  fun enableLocationComponent(loadedMapStyle: Style) {
    //  Get an instance of the component
    val locationComponent = mapboxMap?.locationComponent

    // Set the LocationComponent activation options
    val locationComponentActivationOptions =
      LocationComponentActivationOptions.builder(this, loadedMapStyle)
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
    locationEngine = LocationEngineProvider.getBestLocationEngine(this)
    val request = LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
      .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
      .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build()

    locationEngine?.requestLocationUpdates(request, callback, mainLooper)
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
    toast("PERMISSIONS DENIED") // todo
  }

  @OnNeverAskAgain(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
  )
  fun onNeverAskAgain() {
    toast("PERMISSIONS REQUERED")    // todo
  }

  @OnShowRationale(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
  )
  fun onShowRationale() {
    toast("PERMISSIONS DENIED")   // todo
  }

  companion object {
    private const val DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L
    private const val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5
  }
}

class LocationChangeListener(mainActivity: MainActivity) :
  LocationEngineCallback<LocationEngineResult> {

  private val activityWeakReference: WeakReference<MainActivity> = WeakReference(mainActivity)

  override fun onSuccess(result: LocationEngineResult) {
    activityWeakReference.get()?.mapboxMap?.locationComponent?.forceLocationUpdate(result.lastLocation)
  }

  override fun onFailure(exception: Exception) {
    activityWeakReference.get()?.toast(">>> onFailure: $exception")
  }

}