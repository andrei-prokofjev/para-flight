package com.apro.paraflight.ui.screen


import com.apro.core.ui.BaseFragment
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentMainBinding
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.common.viewBinding


class MainFragment : BaseFragment(R.layout.fragment_main), BackButtonListener {

  private val binding by viewBinding { FragmentMainBinding.bind(it) }


  companion object {

    fun create(): MainFragment = MainFragment()
  }

  override fun onBackPressed(): Boolean {

    DI.appComponent.appRouter().exit()
    return true
  }
}