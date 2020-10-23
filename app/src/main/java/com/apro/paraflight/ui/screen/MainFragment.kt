package com.apro.paraflight.ui.screen


import android.os.Bundle
import android.view.View
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentMainBinding
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.common.viewBinding


class MainFragment : BaseFragment(R.layout.fragment_main), BackButtonListener {

  private val binding by viewBinding { FragmentMainBinding.bind(it) }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      profileImageView.onClick { DI.appComponent.appRouter().navigateTo(Screens.profile()) }

      logbookImageView.onClick { DI.appComponent.appRouter().navigateTo(Screens.logbook()) }

      preflightImageView.onClick { DI.appComponent.appRouter().navigateTo(Screens.preflight()) }
    }
  }


  companion object {

    fun create(): MainFragment = MainFragment()
  }

  override fun onBackPressed(): Boolean {

    DI.appComponent.appRouter().exit()
    return true
  }
}