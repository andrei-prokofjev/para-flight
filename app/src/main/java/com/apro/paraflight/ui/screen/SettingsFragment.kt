package com.apro.paraflight.ui.screen

import com.apro.core.ui.BaseFragment
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentSettingsBinding
import com.apro.paraflight.ui.base.viewBinding

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

  private val binding by viewBinding { FragmentSettingsBinding.bind(it) }
}