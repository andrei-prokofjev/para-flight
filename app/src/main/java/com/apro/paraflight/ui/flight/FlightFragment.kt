package com.apro.paraflight.ui.flight


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.core.util.*
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentFlightBinding
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.common.viewBinding
import kotlin.math.roundToInt


class FlightFragment : BaseFragment(R.layout.fragment_flight), BackButtonListener {

  private lateinit var component: FlightScreenComponent
  private val binding by viewBinding { FragmentFlightBinding.bind(it) }
  private val viewModel by viewModels<FlightScreenViewModel> { component.viewModelFactory() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      viewModel.flightData.observe {

        when (DI.preferencesApi.settings().units) {
          SettingsPreferences.Units.METRIC -> {
            speedMeterView.amount = it.speed.metersPerSecond.convertTo(Speed.KilometerPerHour).amount.roundToInt().toString()
            speedMeterView.unit = getString(R.string.km_h)
            distMeterView.amount = it.dist?.meters?.convertTo(Distance.Kilometer)?.amount?.roundTo(1)?.toString() ?: "-.-"
            distMeterView.unit = getString(R.string.km)
            altitudeMeterView.unit = getString(R.string.m)
            altitudeMeterView.amount = it.alt.roundToInt().toString()
          }
          SettingsPreferences.Units.IMPERIAL -> {
            speedMeterView.amount = it.speed.metersPerSecond.convertTo(Speed.MilePerHour).amount.roundToInt().toString()
            speedMeterView.unit = getString(R.string.mph)
            distMeterView.amount = it.dist?.meters?.convertTo(Distance.Mile)?.amount?.roundTo(1)?.toString() ?: "-.-"
            distMeterView.unit = getString(R.string.ml)
            altitudeMeterView.unit = getString(R.string.ft)
            altitudeMeterView.amount = it.alt.meters.convertTo(Distance.Feet).amount.roundToInt().toString()
          }
        }
        timeMeterView.amount = it.duration?.toTimeFormat() ?: "-:-"
      }

      layerImageView.onClick { viewModel.onLayerClick() }

      viewModel.testData.observe {
        stateTextView.text = it
      }
    }
  }

  override fun onBackPressed(): Boolean {
    //viewModel.land()
    return false
  }

  override fun getViewToApplyStatusBarMargin(root: View): Array<View> = arrayOf(
    binding.altitudeMeterView,
    binding.timeMeterView,
    binding.speedMeterView
  )

  override fun getViewToApplyNavigationBarMargin(root: View): Array<View> = arrayOf(
    binding.distMeterView,
    binding.mv1,
    binding.mv2
  )

  companion object {
    fun create(component: FlightScreenComponent) = FlightFragment().apply {
      this.component = component
    }
  }
}