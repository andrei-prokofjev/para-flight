package com.apro.paraflight.ui.main


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.apro.core.location.engine.impl.MapboxLocationEngine
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentMainBinding
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.common.viewBinding
import com.apro.paraflight.ui.mapbox.MapboxSettings


class MainFragment : BaseFragment(R.layout.fragment_main), BackButtonListener {

  private lateinit var component: MainScreenComponent
  private val binding by viewBinding { FragmentMainBinding.bind(it) }
  private val viewModel by viewModels<MainScreenViewModel> { component.viewModelFactory() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      settingsImageButton.onClick { viewModel.onSettingsClick() }

      logbookImageButton.onClick { viewModel.onLogbookClick() }

      layerImageButton.onClick { viewModel.onLayerClick() }

      myLocationImageButton.onClick { viewModel.onMyLocationClick() }

      preflightImageView.onClick { viewModel.onPreflightClick(MapboxLocationEngine(requireContext())) }

      aboutImageButton.onClick { viewModel.onAboutClick() }
    }
  }

  override fun onResume() {
    super.onResume()
    viewModel.setSettings(MapboxSettings.DefaultMapboxSettings)
  }

  override fun onBackPressed(): Boolean {
    requireActivity().finish()
    return true
  }

  companion object {
    fun create(component: MainScreenComponent) = MainFragment().apply {
      this.component = component
    }
  }

}