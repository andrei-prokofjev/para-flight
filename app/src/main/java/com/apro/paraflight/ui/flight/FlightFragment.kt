package com.apro.paraflight.ui.flight


import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.apro.core.ui.BaseFragment
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentFlightBinding
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.common.viewBinding
import java.util.*


class FlightFragment : BaseFragment(R.layout.fragment_flight), BackButtonListener {

  private val component by lazy { FlightScreenComponent.create() }
  private val binding by viewBinding { FragmentFlightBinding.bind(it) }
  private val viewModel by viewModels<FlightScreenViewModel> { component.viewModelFactory() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


    val fmt = SimpleDateFormat("h:mm", Locale.getDefault())


    with(binding) {
      viewModel.flightData.observe {
        val cal = Calendar.getInstance()
        cal.timeInMillis = it.duration

        println(">>> defalut: " + resources.configuration?.locale)
        println(">>> it $ " + (it.duration / 1000))
        speedMeterView.amount = it.speed.toString()
        altitudeMeterView.amount = it.alt.toString()
        timeMeterView.amount = fmt.format(cal.time)
        distMeterView.amount = it.dist.toString()
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
    fun create(): FlightFragment = FlightFragment()
  }
}