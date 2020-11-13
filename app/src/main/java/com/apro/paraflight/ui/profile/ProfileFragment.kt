package com.apro.paraflight.ui.profile


import android.os.Bundle
import android.view.View
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentProfileBinding
import com.apro.paraflight.ui.Screens
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.common.viewBinding


class ProfileFragment : BaseFragment(R.layout.fragment_profile), BackButtonListener {


  private val binding by viewBinding { FragmentProfileBinding.bind(it) }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      backImageButton.onClick { DI.appComponent.appRouter().exit() }
      settingsImageView.onClick { DI.appComponent.appRouter().navigateTo(Screens.settings()) }
      textView.onClick { DI.appComponent.appRouter().navigateTo(Screens.about()) }
//      button.onClick {
//        if (DI.preferencesApi.settings().voiceGuidance && editText.text.isNullOrEmpty().not())
//          DI.appComponent.voiceGuidance().speak(editText.text.toString())
//      }

    }
  }


  companion object {
    fun create(): ProfileFragment = ProfileFragment()
  }

  override fun onBackPressed(): Boolean {
    return false
  }


}