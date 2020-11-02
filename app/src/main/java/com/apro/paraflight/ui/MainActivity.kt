package com.apro.paraflight.ui


import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import com.apro.core.navigation.AppNavigator
import com.apro.core.ui.toast
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.databinding.ActivityMainBinding
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.mapbox.MapboxScreenComponent
import com.apro.paraflight.ui.mapbox.MapboxViewModel
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
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
class MainActivity : AppCompatActivity() {

  private val navigator = AppNavigator(this, R.id.mainContainerView, supportFragmentManager, supportFragmentManager.fragmentFactory)

  private val component by lazy { MapboxScreenComponent.create() }

  lateinit var binding: ActivityMainBinding

  private val viewModel by viewModels<MapboxViewModel> { component.viewModelFactory() }

  lateinit var mapView: MapView

  lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    AppCenter.start(application, getString(R.string.app_center_access_token), Analytics::class.java, Crashes::class.java)

    Mapbox.getInstance(this, getString(R.string.mapbox_access_token))

    binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
    setContentView(binding.root)

    with(binding) {
      val options = MapboxMapOptions.createFromAttributes(this@MainActivity, null)
        .camera(CameraPosition.Builder()
          .target(LatLng(59.436962, 24.753574))
          .zoom(12.0)
          .build()
        )
      mapView = MapView(this@MainActivity, options).apply {
        onCreate(savedInstanceState)
        mapboxLayout.addView(this)
        getMapAsync {
          mapboxMap = it
          it.setStyle(DI.preferencesApi.mapbox().mapStyle.style) { style ->
            mapboxLayout.findViewWithTag<ImageView>("logoView")?.isVisible = false
            mapboxLayout.findViewWithTag<ImageView>("attrView")?.isVisible = false
            mapboxLayout.findViewWithTag<ImageView>("compassView")?.isVisible = false
            enableLocationComponentWithPermissionCheck(style)
            style.addSource(GeoJsonSource(ROUTE_SOURCE_ID))
            style.addLayer(LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID).withProperties(
              lineWidth(5f),
              lineColor(Color.BLUE)
            ))
          }
        }
      }
    }
    // set map style
    viewModel.style.observe { mapboxMap.setStyle(it) }

    // my current position
    viewModel.myCurrentPosition.observe { mapboxMap.animateCamera(it.first, it.second) }

    // update location
    viewModel.locationData.observe {
      //mapboxMap.locationComponent.forceLocationUpdate(it)


      mapboxMap.cameraPosition = CameraPosition.Builder().target(LatLng(it)).build()
    }
    // draw route
    viewModel.routeData.observe {
      mapboxMap.style?.let { style ->
        val source = style.getSourceAs<GeoJsonSource>(ROUTE_SOURCE_ID)
        source?.setGeoJson(FeatureCollection.fromFeatures(arrayOf(Feature.fromGeometry(LineString.fromLngLats(it)))))
      }
    }

    DI.appComponent.appRouter().newRootScreen(Screens.main())
  }


  override fun onBackPressed() {
    supportFragmentManager.findFragmentById(R.id.mainContainerView)?.let {
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

  private inline fun <T> LiveData<T>.observe(crossinline observer: (T) -> Unit) {
    observe(this@MainActivity, { observer.invoke(it) })
  }

  companion object {
    private const val ROUTE_SOURCE_ID = "route-source"
    private const val ROUTE_LAYER_ID = "route-layer"
  }
}
