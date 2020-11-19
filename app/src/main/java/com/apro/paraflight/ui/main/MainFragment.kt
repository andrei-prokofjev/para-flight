package com.apro.paraflight.ui.main


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.apro.core.location.engine.impl.FileLocationEngine
import com.apro.core.location.engine.impl.MapboxLocationEngine
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentMainBinding
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.common.viewBinding
import com.apro.paraflight.ui.mapbox.MapboxSettings
import java.io.File


class MainFragment : BaseFragment(R.layout.fragment_main), BackButtonListener {

  private lateinit var component: MainScreenComponent
  private val binding by viewBinding { FragmentMainBinding.bind(it) }
  private val viewModel by viewModels<MainScreenViewModel> { component.viewModelFactory() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      settingsImageButton.onClick { viewModel.onSettingsClick() }
      logbookImageButton.onClick {
        val file = File("vormsi20200624.csv")
        viewModel.onLogbookClick(FileLocationEngine(requireContext(), file))
      }
      layerImageButton.onClick { viewModel.onLayerClick() }
      myLocationImageButton.onClick { viewModel.onMyLocationClick() }
      preflightImageView.onClick { viewModel.onPreflightClick(MapboxLocationEngine(requireContext())) }
    }
  }

  override fun onResume() {
    super.onResume()
    viewModel.setSettings(MapboxSettings.DefaultMapboxSettings)
  }

  companion object {
    fun create(component: MainScreenComponent) = MainFragment().apply {
      this.component = component
    }
  }

  override fun onBackPressed(): Boolean {
    requireActivity().finish()
    return true
  }
}