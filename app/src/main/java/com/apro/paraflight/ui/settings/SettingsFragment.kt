package com.apro.paraflight.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentSettingsBinding
import com.apro.paraflight.ui.common.viewBinding


class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

  private val component by lazy { SettingsScreenComponent.create() }
  private val binding by viewBinding { FragmentSettingsBinding.bind(it) }
  private val viewModel by viewModels<SettingsScreenViewModel> { component.viewModelFactory() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      backImageView.onClick { viewModel.onBackClicked() }

      takeOffSpeedPicker.minValue = SettingsPreferences.MIN_TAKE_OFF_SPEED
      takeOffSpeedPicker.maxValue = SettingsPreferences.MAX_TAKE_OFF_SPEED
      takeOffSpeedPicker.wrapSelectorWheel = false
      takeOffSpeedPicker.setOnValueChangedListener { _, _, new -> viewModel.saveTakeOffSpeed(new) }
      viewModel.takeOffSpeedData.observe { takeOffSpeedPicker.value = it }

      takeOffAltDiffPicker.minValue = SettingsPreferences.MIN_TAKE_OFF_ALT_DIFF
      takeOffAltDiffPicker.maxValue = SettingsPreferences.MAX_TAKE_OFF_ALT_DIFF
      takeOffAltDiffPicker.wrapSelectorWheel = false
      takeOffAltDiffPicker.setOnValueChangedListener { _, _, new -> viewModel.saveTakeOffAlt(new) }
      viewModel.takeOffAltDiffData.observe { takeOffAltDiffPicker.value = it }

      minFlightSpeedPicker.minValue = SettingsPreferences.MIN_MIN_FLIGHT_SPEED
      minFlightSpeedPicker.maxValue = SettingsPreferences.MAX_MIN_FLIGHT_SPEED
      minFlightSpeedPicker.wrapSelectorWheel = false
      minFlightSpeedPicker.setOnValueChangedListener { _, _, new -> viewModel.saveMinFlightSpeed(new) }
      viewModel.minFlightSpeedData.observe { minFlightSpeedPicker.value = it }

      voiceGuidanceSwitch.setOnCheckedChangeListener { _, on -> viewModel.saveVoiceGuidance(on) }
      viewModel.voiceGuidanceData.observe { voiceGuidanceSwitch.isChecked = it }

      viewModel.unitsData.observe {
        when (it) {
          SettingsPreferences.Units.METRIC -> unitsRadioGroup.check(R.id.metricRadioButton)
          SettingsPreferences.Units.IMPERIAL -> unitsRadioGroup.check(R.id.imperialRadioButton)
        }
      }
      unitsRadioGroup.setOnCheckedChangeListener { _, checkedId ->
        when (checkedId) {
          R.id.metricRadioButton -> viewModel.saveUnits(SettingsPreferences.Units.METRIC)
          R.id.imperialRadioButton -> viewModel.saveUnits(SettingsPreferences.Units.IMPERIAL)
        }
      }

    }
  }

  companion object {
    fun create() = SettingsFragment()
  }


}