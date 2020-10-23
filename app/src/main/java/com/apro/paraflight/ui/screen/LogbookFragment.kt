package com.apro.paraflight.ui.screen

import android.os.Bundle
import android.view.View
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentLogbookBinding
import com.apro.paraflight.ui.common.viewBinding

class LogbookFragment : BaseFragment(R.layout.fragment_logbook) {

  private val binding by viewBinding { FragmentLogbookBinding.bind(it) }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      backImageView.onClick { DI.appComponent.appRouter().exit() }
    }
  }


  companion object {
    fun create() = LogbookFragment()
  }


}