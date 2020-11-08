package com.apro.paraflight.ui.flight


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentFlightBinding
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.common.viewBinding


class FlightFragment : BaseFragment(R.layout.fragment_flight), BackButtonListener {

  private lateinit var component: FlightScreenComponent
  private val binding by viewBinding { FragmentFlightBinding.bind(it) }
  private val viewModel by viewModels<FlightScreenViewModel> { component.viewModelFactory() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      viewModel.flightData.observe {
        speedMeterView.amount = it.speed
        altitudeMeterView.amount = it.alt
        timeMeterView.amount = it.duration
        distMeterView.amount = it.dist
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