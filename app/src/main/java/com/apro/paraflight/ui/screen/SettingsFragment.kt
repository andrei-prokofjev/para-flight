package com.apro.paraflight.ui.screen

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import com.apro.core.ui.BaseFragment
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentMainBinding
import com.apro.paraflight.ui.base.viewBinding
import com.apro.paraflight.viewmodel.main.MainScreenComponent
import com.apro.paraflight.viewmodel.main.MapboxViewModel

class SettingsFragment : BaseFragment(R.layout.fragment_main) {
  private val component by lazy { MainScreenComponent.create() }
  private val binding by viewBinding { FragmentMainBinding.bind(it) }
  private val viewModel by viewModels<MapboxViewModel> { component.viewModelFactory() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val layout = FrameLayout(requireContext()).apply {

      setBackgroundColor(Color.RED)

    }

    binding.mapboxLayout.addView(layout)

  }


  ond
}