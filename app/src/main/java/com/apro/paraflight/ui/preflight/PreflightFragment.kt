package com.apro.paraflight.ui.preflight

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentPreflightBinding
import com.apro.paraflight.ui.common.viewBinding

class PreflightFragment : BaseFragment(R.layout.fragment_preflight) {

  private val binding by viewBinding { FragmentPreflightBinding.bind(it) }
  private val component by lazy { PreflightScreenComponent.create() }
  private val viewModel by viewModels<PreflightScreenViewModel> { component.viewModelFactory() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      flightImageView.onClick { viewModel.onFlightClick() }
    }

  }


  companion object {
    fun create() = PreflightFragment()
  }
}