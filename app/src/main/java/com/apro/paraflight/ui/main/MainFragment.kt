package com.apro.paraflight.ui.main


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentMainBinding
import com.apro.paraflight.ui.common.BackButtonListener
import com.apro.paraflight.ui.common.viewBinding


class MainFragment : BaseFragment(R.layout.fragment_main), BackButtonListener {

  private val component by lazy { MainScreenComponent.create() }
  private val binding by viewBinding { FragmentMainBinding.bind(it) }
  private val viewModel by viewModels<MainScreenViewModel> { component.viewModelFactory() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      settingsImageView.onClick { viewModel.onSettingsClick() }
      logbookImageView.onClick { viewModel.onLogbookClick() }
      layerImageView.onClick { viewModel.onLayerClick() }
      myLocationImageView.onClick { viewModel.onMyLocationClick() }
      preflightImageView.onClick { viewModel.onPreflightClick() }
    }
  }


  companion object {
    fun create(): MainFragment = MainFragment()
  }

  override fun onBackPressed(): Boolean {
    requireActivity().finish()
    return true
  }
}