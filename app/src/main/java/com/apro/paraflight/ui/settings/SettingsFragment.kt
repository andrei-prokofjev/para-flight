package com.apro.paraflight.ui.settings

import android.os.Bundle
import android.view.View
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentSettingsBinding
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.common.viewBinding

class SettingsFragment : BaseFragment(R.layout.fragment_settings), BackButtonListener {


  private val binding by viewBinding { FragmentSettingsBinding.bind(it) }


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      backImageView.onClick { DI.appComponent.appRouter().exit() }
    }

  }

  override fun onBackPressed(): Boolean {

    println(">>> save settings$")
    return false
  }

  companion object {
    fun create() = SettingsFragment()
  }


}