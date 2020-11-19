package com.apro.paraflight.ui


import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.apro.core.location.engine.impl.MapboxLocationEngine
import com.apro.core.navigation.AppNavigator
import com.apro.core.ui.show
import com.apro.core.ui.toast
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.databinding.ActivityMainBinding
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.mapbox.MapboxScreenComponent
import com.apro.paraflight.ui.mapbox.MapboxSettings
import com.apro.paraflight.ui.mapbox.MapboxViewModel
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.CompassListener
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
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

  private val component by lazy { MapboxScreenComponent.create(MapboxLocationEngine(this)) }

  lateinit var binding: ActivityMainBinding

  private val viewModel by viewModels<MapboxViewModel> { component.viewModelFactory() }

  lateinit var mapView: MapView

  private var mapboxMap: MapboxMap? = null

  private val compassListener: CompassListener = object : CompassListener {
    override fun onCompassChanged(userHeading: Float) {
      AnimatorSet().apply {
        play(
          ObjectAnimator.ofFloat(binding.compassView, View.ROTATION, binding.compassView.rotation, -userHeading).apply {
            duration = 800
          }
        )
        start()
      }
    }

    override fun onCompassAccuracyChange(compassStatus: Int) {
    }

  }

  @SuppressLint("MissingPermission")
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

          it.addOnMapClickListener { l ->

            viewModel.myCurrentPosition

            DI.appComponent.mapboxInteractor().requestLastLocation(MapboxLocationEngine(context))
            true
          }

          updateSettings(MapboxSettings.DefaultMapboxSettings)

          with(it.uiSettings) {
            isLogoEnabled = false
            isAttributionEnabled = false
            isCompassEnabled = false
          }

          it.setStyle(DI.preferencesApi.mapbox().mapStyle.style) { style ->
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
    viewModel.styleData.observe { mapboxMap?.setStyle(it) }

    // my current position
    viewModel.myCurrentPosition.observe { mapboxMap?.animateCamera(it.first, it.second) }

    // update location
    viewModel.locationData.observe { location ->
      mapboxMap?.locationComponent?.forceLocationUpdate((location))

      mapboxMap?.let {
        with(it.locationComponent) {
          cameraMode = CameraMode.TRACKING_GPS
          // renderMode = RenderMode.GPS
          forceLocationUpdate(location)
        }
      }
    }
    // draw route
    viewModel.routeData.observe {
      mapboxMap?.style?.let { style ->
        val source = style.getSourceAs<GeoJsonSource>(ROUTE_SOURCE_ID)
        source?.setGeoJson(FeatureCollection.fromFeatures(arrayOf(Feature.fromGeometry(LineString.fromLngLats(it)))))
      }
    }
    // ui settings
    viewModel.mapboxSettingsData.observe { updateSettings(it) }

    DI.appComponent.appRouter().newRootScreen(Screens.main(MapboxLocationEngine(this)))
  }

  @SuppressLint("MissingPermission")
  private fun updateSettings(settings: MapboxSettings) {
    mapboxMap?.let {
      with(it.locationComponent) {
        if (isLocationComponentActivated) {
          isLocationComponentEnabled = settings.locationComponentEnabled
          if (settings.compassEnabled) {
            compassEngine?.addCompassListener(compassListener)
          } else {
            compassEngine?.removeCompassListener(compassListener)
          }
          println(">>> set camera $ + " + settings.cameraMode)
          renderMode = settings.renderMode.mode
          cameraMode = settings.cameraMode.mode
        }
      }

      binding.compassView.show(settings.compassEnabled)

      with(it.uiSettings) {
        isDisableRotateWhenScaling = settings.disableRotateWhenScaling
        isRotateGesturesEnabled = settings.rotateGesturesEnabled
        isTiltGesturesEnabled = settings.tiltGesturesEnabled
        isZoomGesturesEnabled = settings.zoomGesturesEnabled
        isScrollGesturesEnabled = settings.scrollGesturesEnabled
        isHorizontalScrollGesturesEnabled = settings.horizontalScrollGesturesEnabled
        isDoubleTapGesturesEnabled = settings.doubleTapGesturesEnabled
        isQuickZoomGesturesEnabled = settings.quickZoomGesturesEnabled
        isScaleVelocityAnimationEnabled = settings.scaleVelocityAnimationEnabled
        isRotateVelocityAnimationEnabled = settings.rotateVelocityAnimationEnabled
        isFlingVelocityAnimationEnabled = settings.flingVelocityAnimationEnabled
        isIncreaseScaleThresholdWhenRotating = settings.increaseScaleThresholdWhenRotating
      }

      it.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.Builder()
        .zoom(settings.zoom)
        .build()), 500)
    }
  }


  @SuppressLint("MissingPermission")
  @NeedsPermission(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
  )
  fun enableLocationComponent(loadedMapStyle: Style) {
    val locationComponent = mapboxMap?.locationComponent
    val locationComponentActivationOptions = LocationComponentActivationOptions
      .builder(this, loadedMapStyle)
      .useDefaultLocationEngine(true)
      .build()

    locationComponent?.activateLocationComponent(locationComponentActivationOptions)
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


