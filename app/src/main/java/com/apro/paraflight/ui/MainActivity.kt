package com.apro.paraflight.ui


import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.apro.core.ui.toast
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.databinding.ActivityMainBinding
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.screen.Screens
import com.apro.paraflight.viewmodel.MapboxScreenComponent
import com.apro.paraflight.viewmodel.MapboxViewModel
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mapbox.android.core.location.*
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMapOptions
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import permissions.dispatcher.*

@RuntimePermissions
class MainActivity : AppCompatActivity(), LocationEngineCallback<LocationEngineResult> {

  private val navigator: Navigator = AppNavigator(this, R.id.main_container, supportFragmentManager, supportFragmentManager.fragmentFactory)

  private val component by lazy { MapboxScreenComponent.create() }

  lateinit var binding: ActivityMainBinding

  private val viewModel by viewModels<MapboxViewModel> { component.viewModelFactory() }

  lateinit var mapView: MapView

  lateinit var mapboxMap: MapboxMap

  var locationEngine: LocationEngine? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    AppCenter.start(application, getString(R.string.app_center_access_token), Analytics::class.java, Crashes::class.java)

    Mapbox.getInstance(this, getString(R.string.mapbox_access_token))

    binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
    setContentView(binding.root)

    with(binding) {
      val options = MapboxMapOptions.createFromAttributes(this@MainActivity, null)
        .camera(
          CameraPosition.Builder()
            .target(LatLng(59.436962, 24.753574))
            .zoom(12.0)
            .build()
        )

      mapView = MapView(this@MainActivity, options).apply {
        onCreate(savedInstanceState)
        mapboxLayout.addView(this)
        getMapAsync {

          mapboxMap = it
          it.setStyle(viewModel.getStyle(DI.preferencesApi.mapbox().mapStyle)) { style ->
            enableLocationComponentWithPermissionCheck(style)

            initLocationEngine()

            style.addSource(GeoJsonSource(ROUTE_SOURCE_ID))
            style.addLayer(LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID).withProperties(
              lineWidth(5f),
              lineColor(Color.BLUE)
            ))
          }
        }
      }
    }

    viewModel.style.observe {
      println(">>> set stuyle: $it")
      mapboxMap.setStyle(it)
    }

    DI.appComponent.appRouter().newRootScreen(Screens.main())
  }


  override fun onBackPressed() {
    supportFragmentManager.findFragmentById(R.id.main_container)?.let {
      if (it is BackButtonListener && it.onBackPressed()) return else super.onBackPressed()
    } ?: super.onBackPressed()
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onResume() {
    super.onResume()
    DI.appComponent.navigatorHolder().setNavigator(navigator)
    mapView.onResume()
  }

  override fun onPause() {
    DI.appComponent.navigatorHolder().removeNavigator()
    mapView.onPause()
    super.onPause()
  }

  override fun onStop() {
    mapView.onStop()
    super.onStop()
  }

  override fun onDestroy() {
    mapView.onDestroy()
    super.onDestroy()
  }

  override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
    super.onSaveInstanceState(outState, outPersistentState)
    mapView.onSaveInstanceState(outState)
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
    val locationComponent = mapboxMap.locationComponent
    val locationComponentActivationOptions = LocationComponentActivationOptions
      .builder(this, loadedMapStyle)
      .useDefaultLocationEngine(false)
      .build()

    locationComponent.let {
      it.activateLocationComponent(locationComponentActivationOptions)
      it.isLocationComponentEnabled = true
      it.cameraMode = CameraMode.TRACKING
      it.renderMode = RenderMode.COMPASS
    }
  }

  @SuppressLint("MissingPermission")
  private fun initLocationEngine() {

    locationEngine = LocationEngineProvider.getBestLocationEngine(this)
    val request = LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
      .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
      .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME)
      .build()

    locationEngine?.requestLocationUpdates(request, this, mainLooper)
    locationEngine?.getLastLocation(this)
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

  override fun onSuccess(result: LocationEngineResult) {
    result.lastLocation?.let { viewModel.updateLocation(it) }

    mapboxMap.locationComponent.forceLocationUpdate(result.lastLocation)
    mapboxMap.cameraPosition = CameraPosition.Builder().target(LatLng(result.lastLocation)).build()
  }

  override fun onFailure(exception: Exception) {

  }

  inline fun <T> LiveData<T>.observe(crossinline observer: (T) -> Unit) {
    observe(this@MainActivity, { observer.invoke(it) })
  }

  companion object {
    private const val DEFAULT_INTERVAL_IN_MILLISECONDS = 2000L
    private const val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5

    private const val ROUTE_SOURCE_ID = "route-source"
    private const val ROUTE_LAYER_ID = "route-layer"
  }
}
