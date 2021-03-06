package com.apro.paraflight.ui.flight


import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.core.ui.onLongClick
import com.apro.core.util.*
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentFlightBinding
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.common.viewBinding
import com.mapbox.mapboxsdk.Mapbox.getApplicationContext
import kotlin.math.roundToInt


class FlightFragment : BaseFragment(R.layout.fragment_flight), BackButtonListener {

  private lateinit var component: FlightScreenComponent
  private val binding by viewBinding { FragmentFlightBinding.bind(it) }
  private val viewModel by viewModels<FlightScreenViewModel> { component.viewModelFactory() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      viewModel.flightData.observe {

        when (DI.appComponent.settingsPreferences().units) {
          SettingsPreferences.Units.METRIC -> {
            speedMeterView.amount = it.groundSpeed.metersPerSecond.convertTo(Speed.KilometerPerHour).amount.roundToInt().toString()
            speedMeterView.unit = getString(R.string.km_h)
            distMeterView.amount = it.dist?.meters?.convertTo(Distance.Kilometer)?.amount?.roundTo(1)?.toString() ?: "-.-"
            distMeterView.unit = getString(R.string.km)
            altitudeMeterView.unit = getString(R.string.m)
            altitudeMeterView.amount = if (it.alt == 0.0) "--" else it.alt.roundTo(1).toString()
          }
          SettingsPreferences.Units.IMPERIAL -> {
            speedMeterView.amount = it.groundSpeed.metersPerSecond.convertTo(Speed.MilePerHour).amount.roundToInt().toString()
            speedMeterView.unit = getString(R.string.mph)
            distMeterView.amount = it.dist?.meters?.convertTo(Distance.Mile)?.amount?.roundTo(1)?.toString() ?: "-.-"
            distMeterView.unit = getString(R.string.ml)
            altitudeMeterView.unit = getString(R.string.ft)
            altitudeMeterView.amount = if (it.alt == 0.0) "--" else it.alt.meters.convertTo(Distance.Feet).amount.roundToInt().toString()
          }
        }
        timeMeterView.amount = it.duration?.toTimeFormat() ?: "-:-"

        windSpeedTextView.text = it.windSpeed?.roundTo(1)?.toString() ?: "-"
        windSpeedTextView.isVisible = DI.appComponent.settingsPreferences().windDetector

        it.winDirection?.let {
          windDirectionView.rotation = it
        }
        windDirectionView.isVisible = it.winDirection != null && DI.appComponent.settingsPreferences().windDetector
      }

      layerImageButton.onClick { viewModel.onLayerClick() }

      viewModel.testData.observe {
        stateTextView.text = it
      }

      altitudeMeterView.onLongClick {
        DI.appComponent.appRouter().openModalDialogFragment(AltitudeCalibrationDialog().apply {
          setCalibrateClick {
            DI.appComponent.settingsPreferences().altitudeOffset = 20
            viewModel.calibrate()
          }
        })
      }


      viewModel.dopData.observe {

        val color = if (it?.baseAlt == 0.0) {
          when (it.verticalDop.roundToInt()) {
            0, 1, 2 -> android.R.color.holo_green_dark
            3, 4, 5 -> android.R.color.holo_orange_dark
            else -> android.R.color.holo_red_dark
          }
        } else android.R.color.holo_blue_dark

        dopView.backgroundTintList = ContextCompat.getColorStateList(getApplicationContext(), color)
      }
    }
  }

  override fun onBackPressed(): Boolean {
    return false
  }

  override fun getViewToApplyStatusBarMargin(root: View): Array<View> = arrayOf(
    binding.altitudeMeterView,
    binding.timeMeterView,
    binding.speedMeterView
  )

  override fun getViewToApplyNavigationBarMargin(root: View): Array<View> = arrayOf(
    binding.distMeterView,
    binding.cameraModeMeterView,
    binding.renderModeMeterView
  )

  companion object {
    fun create(component: FlightScreenComponent) = FlightFragment().apply {
      this.component = component
    }
  }
}