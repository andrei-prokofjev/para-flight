package com.apro.paraflight.ui.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.apro.core.preferenes.api.SettingsPreferences
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentSettingsBinding
import com.apro.paraflight.ui.common.viewBinding


class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

  private val component by lazy { SettingsScreenComponent.create() }
  private val binding by viewBinding { FragmentSettingsBinding.bind(it) }
  private val viewModel by viewModels<SettingsScreenViewModel> { component.viewModelFactory() }

  @SuppressLint("DiscouragedPrivateApi")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      // googleSignButton.onClick { viewModel.googleSignIn() }
      backImageButton.onClick { viewModel.onBackClicked() }

      with(takeOffDetectionLayout) {
        takeOffSpeedPicker.minValue = SettingsPreferences.MIN_TAKE_OFF_SPEED
        takeOffSpeedPicker.maxValue = SettingsPreferences.MAX_TAKE_OFF_SPEED
        takeOffSpeedPicker.wrapSelectorWheel = false
        takeOffSpeedPicker.setOnValueChangedListener { _, _, new -> viewModel.saveTakeOffSpeed(new) }
        takeOffSpeedPicker.value = DI.appComponent.settingsPreferences().takeOffSpeed

        takeOffAltDiffPicker.minValue = SettingsPreferences.MIN_TAKE_OFF_ALT_DIFF
        takeOffAltDiffPicker.maxValue = SettingsPreferences.MAX_TAKE_OFF_ALT_DIFF
        takeOffAltDiffPicker.wrapSelectorWheel = false
        takeOffAltDiffPicker.setFormatter { it.toString() + resources.getString(R.string.m) }
        try {
          takeOffAltDiffPicker.javaClass.getDeclaredMethod("changeValueByOne", Boolean::class.javaPrimitiveType).apply {
            isAccessible = true
            invoke(takeOffAltDiffPicker, true)
          }
        } catch (e: Exception) {
          // ignore
        }
        takeOffAltDiffPicker.value = DI.appComponent.settingsPreferences().takeOffAltDiff
        takeOffAltDiffPicker.setOnValueChangedListener { _, _, new -> viewModel.saveTakeOffAlt(new) }

        minFlightSpeedPicker.minValue = SettingsPreferences.MIN_MIN_FLIGHT_SPEED
        minFlightSpeedPicker.maxValue = SettingsPreferences.MAX_MIN_FLIGHT_SPEED
        minFlightSpeedPicker.wrapSelectorWheel = false
        minFlightSpeedPicker.setOnValueChangedListener { _, _, new -> viewModel.saveMinFlightSpeed(new) }
        minFlightSpeedPicker.value = DI.appComponent.settingsPreferences().minFlightSpeed
      }

      with(voiceGuidanceLayout) {
        voiceGuidanceSwitch.setOnCheckedChangeListener { _, on -> viewModel.saveVoiceGuidance(on) }
        voiceGuidanceSwitch.isChecked = DI.appComponent.settingsPreferences().voiceGuidance
      }

      with(windDetectorLayout) {
        windDetectorSwitch.setOnCheckedChangeListener { _, on -> viewModel.saveWindDetector(on) }
        windDetectorSwitch.isChecked = DI.appComponent.settingsPreferences().windDetector

        pointsNumberPicker.minValue = SettingsPreferences.MIN_WIND_DETECTION_POINTS
        pointsNumberPicker.maxValue = SettingsPreferences.MAX_WIND_DETECTION_POINTS
        pointsNumberPicker.wrapSelectorWheel = false

        pointsNumberPicker.setOnValueChangedListener { _, _, new -> viewModel.saveWindDetectorPoints(new) }
        pointsNumberPicker.setFormatter { (it * SettingsPreferences.WIND_DIRECTION_POINTS_STEP).toString() }

        try {
          pointsNumberPicker.javaClass.getDeclaredMethod("changeValueByOne", Boolean::class.javaPrimitiveType).apply {
            isAccessible = true
            invoke(pointsNumberPicker, true)
          }
        } catch (e: Exception) {
          // ignore
        }

        pointsNumberPicker.value = DI.appComponent.settingsPreferences().windDetectionPoints
      }

      with(unitsLayout) {
        when (DI.appComponent.settingsPreferences().units) {
          SettingsPreferences.Units.METRIC -> unitsRadioGroup.check(R.id.metricRadioButton)
          SettingsPreferences.Units.IMPERIAL -> unitsRadioGroup.check(R.id.imperialRadioButton)
        }
        unitsRadioGroup.setOnCheckedChangeListener { _, checkedId ->
          when (checkedId) {
            R.id.metricRadioButton -> viewModel.saveUnits(SettingsPreferences.Units.METRIC)
            R.id.imperialRadioButton -> viewModel.saveUnits(SettingsPreferences.Units.IMPERIAL)
          }
        }
      }
    }
  }

  override fun getViewToApplyStatusBarMargin(root: View): Array<View> = arrayOf(binding.settingsScrollView)

  override fun getViewToApplyNavigationBarMargin(root: View): Array<View> = arrayOf(binding.settingsScrollView)

  companion object {
    fun create() = SettingsFragment()
  }


}