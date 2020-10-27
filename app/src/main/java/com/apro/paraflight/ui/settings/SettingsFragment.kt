package com.apro.paraflight.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentSettingsBinding
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.common.viewBinding


class SettingsFragment : BaseFragment(R.layout.fragment_settings), BackButtonListener {

  private val component by lazy { SettingsScreenComponent.create() }
  private val binding by viewBinding { FragmentSettingsBinding.bind(it) }
  private val viewModel by viewModels<SettingsScreenViewModel> { component.viewModelFactory() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      backImageView.onClick {
        viewModel.saveTakeOffDetectionParams(takeOffSpeedPicker.value, takeOffAltDiffPicker.value)
        viewModel.onBackClicked()
      }

      takeOffSpeedPicker.minValue = SettingsPreferences.MIN_TAKE_OFF_SPEED
      takeOffSpeedPicker.maxValue = SettingsPreferences.MAX_TAKE_OFF_SPEED
      takeOffSpeedPicker.wrapSelectorWheel = false
      viewModel.takeOffSpeedData.observe { takeOffSpeedPicker.value = it }

      takeOffAltDiffPicker.minValue = SettingsPreferences.MIN_TAKE_OFF_ALT_DIFF
      takeOffAltDiffPicker.maxValue = SettingsPreferences.MAX_TAKE_OFF_ALT_DIFF
      takeOffAltDiffPicker.wrapSelectorWheel = false
      viewModel.takeOffAltDiffData.observe { takeOffAltDiffPicker.value = it }

      voiceGuidanceSwitch.setOnCheckedChangeListener { _, on -> viewModel.saveVoiceGuidance(on) }
      viewModel.voiceGuidanceData.observe { voiceGuidanceSwitch.isChecked = it }

    }
  }

  override fun onBackPressed(): Boolean {
    viewModel.saveTakeOffDetectionParams(binding.takeOffSpeedPicker.value, binding.takeOffAltDiffPicker.value)
    return false
  }

  companion object {
    fun create() = SettingsFragment()
  }


}