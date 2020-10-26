package com.apro.paraflight.ui.screen


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.apro.core.ui.BaseFragment
import com.apro.core.util.Speed
import com.apro.core.util.metersPerSecond
import com.apro.paraflight.DI
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
      viewModel.locationData.observe {
        speedMeterView.amount = it.speed.metersPerSecond.convertTo(Speed.KilometerPerHour).amount.toInt().toString()
        altitudeMeterView.amount = it.altitude.toInt().toString()
      }

      viewModel.distData.observe {
        distMeterView.amount = it.toInt().toString()
      }
    }
  }

  override fun onBackPressed(): Boolean {
    viewModel.land()
    DI.appComponent.appRouter().backTo(Screens.main())
    return true
  }

  override fun getViewToApplyStatusBarMargin(root: View): Array<View> = arrayOf(
    binding.altitudeMeterView,
    binding.distMeterView,
    binding.speedMeterView
  )

  companion object {
    fun create(): FlightFragment = FlightFragment()
  }
}