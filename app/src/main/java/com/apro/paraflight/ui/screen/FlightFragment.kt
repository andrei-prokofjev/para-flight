package com.apro.paraflight.ui.screen

import com.apro.core.ui.BaseFragment
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentFlightBinding
import com.apro.paraflight.ui.base.viewBinding

class FlightFragment : BaseFragment(R.layout.fragment_flight) {

  private val binding by viewBinding { FragmentFlightBinding.bind(it) }

  companion object {
    fun newInstance() = FlightFragment()
  }
}