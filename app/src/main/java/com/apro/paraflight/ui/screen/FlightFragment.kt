package com.apro.paraflight.ui.screen


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.apro.core.ui.BaseFragment
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentFlightBinding
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.common.viewBinding
import com.apro.paraflight.viewmodel.FlightScreenComponent
import com.apro.paraflight.viewmodel.FlightScreenViewModel


class FlightFragment : BaseFragment(R.layout.fragment_flight), BackButtonListener {

  private val component by lazy { FlightScreenComponent.create() }
  private val binding by viewBinding { FragmentFlightBinding.bind(it) }
  private val viewModel by viewModels<FlightScreenViewModel> { component.viewModelFactory() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {

    }
  }


//      myLocationImageView.onClick {
//        viewModel.locationData.value?.let {
//          val position = CameraPosition.Builder()
//            .target(LatLng(it.latitude, it.longitude))
//            .build()
//          mapboxMap?.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000)
//        }
//      }



  // location observer
//      viewModel.locationData.observe {
//        mapboxMap?.locationComponent?.forceLocationUpdate(it)
//        mapboxMap?.cameraPosition = CameraPosition.Builder().target(LatLng(it)).build()
//
//        val lastPoint = Point.fromLngLat(it.longitude, it.latitude)
//        routeCoordinates.add(lastPoint)
//
//        val dist = TurfMeasurement.distance(routeCoordinates[0], lastPoint, TurfConstants.UNIT_METERS)
//
//        mapboxMap?.style?.let { style ->
//          val source = style.getSourceAs<GeoJsonSource>(ROUTE_SOURCE_ID)
//          source?.setGeoJson(FeatureCollection.fromFeatures(arrayOf(Feature.fromGeometry(LineString.fromLngLats(routeCoordinates)))))
//        }
//      }


  override fun onBackPressed(): Boolean {
    viewModel.land()
    return true
  }

  companion object {
    fun create(): FlightFragment = FlightFragment()
  }


}