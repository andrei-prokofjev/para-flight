package com.apro.paraflight.ui.screen

import android.os.Bundle
import android.view.View
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentPreflightBinding
import com.apro.paraflight.ui.common.viewBinding

class PreflightFragment : BaseFragment(R.layout.fragment_preflight) {

  private val binding by viewBinding { FragmentPreflightBinding.bind(it) }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      flightImageView.onClick { DI.appComponent.appRouter().replaceScreen(Screens.flight()) }
    }

  }


  companion object {
    fun create() = PreflightFragment()
  }
}