package com.apro.paraflight.ui.about

import android.os.Bundle
import android.view.View
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.paraflight.BuildConfig
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentAboutBinding
import com.apro.paraflight.ui.common.viewBinding

class AboutFragment : BaseFragment(R.layout.fragment_about) {

  private val binding by viewBinding { FragmentAboutBinding.bind(it) }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      versionTextView.text = BuildConfig.VERSION_NAME
      backImageView.onClick { DI.appComponent.appRouter().exit() }
    }
  }

  companion object {
    fun create() = AboutFragment()
  }


}