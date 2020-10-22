package com.apro.paraflight.ui.screen

import com.apro.core.ui.BaseFragment
import com.apro.paraflight.R
import com.apro.paraflight.ui.BackButtonListener

class SettingsFragment : BaseFragment(R.layout.fragment_settings), BackButtonListener {


  override fun onBackPressed(): Boolean {

    return false
  }

  companion object {
    fun newInstance() = SettingsFragment()
  }


}