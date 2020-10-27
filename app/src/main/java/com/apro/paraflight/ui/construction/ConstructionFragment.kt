package com.apro.paraflight.ui.construction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.apro.core.preferenes.api.ConstructionPreferences
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentConstructionBinding
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.common.viewBinding


class ConstructionFragment : BaseFragment(R.layout.fragment_construction), BackButtonListener {

  private val component by lazy { ConstructionScreenComponent.create() }
  private val binding by viewBinding { FragmentConstructionBinding.bind(it) }
  private val viewModel by viewModels<ConstructionScreenViewModel> { component.viewModelFactory() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      backImageView.onClick {
        viewModel.saveConstruction(takeOffSpeedPicker.value, takeOffAltDiffPicker.value)
        viewModel.onBackClicked()
      }

      takeOffSpeedPicker.minValue = ConstructionPreferences.MIN_TAKE_OFF_SPEED
      takeOffSpeedPicker.maxValue = ConstructionPreferences.MAX_TAKE_OFF_SPEED
      takeOffSpeedPicker.wrapSelectorWheel = false

      viewModel.takeOffSpeedData.observe {
        takeOffSpeedPicker.value = it
      }

      takeOffAltDiffPicker.minValue = ConstructionPreferences.MIN_TAKE_OFF_ALT_DIFF
      takeOffAltDiffPicker.maxValue = ConstructionPreferences.MAX_TAKE_OFF_ALT_DIFF
      takeOffAltDiffPicker.wrapSelectorWheel = false

      viewModel.takeOffAltDiffData.observe {
        takeOffAltDiffPicker.value = it
      }
    }
  }

  override fun onBackPressed(): Boolean {
    viewModel.saveConstruction(binding.takeOffSpeedPicker.value, binding.takeOffAltDiffPicker.value)
    return false
  }

  companion object {
    fun create() = ConstructionFragment()
  }


}