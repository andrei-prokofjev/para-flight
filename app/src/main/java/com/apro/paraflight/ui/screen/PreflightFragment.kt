package com.apro.paraflight.ui.screen

import com.apro.core.ui.BaseFragment
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentPreflightBinding
import com.apro.paraflight.ui.base.viewBinding

class PreflightFragment : BaseFragment(R.layout.fragment_preflight) {

  private val binding by viewBinding { FragmentPreflightBinding.bind(it) }
}