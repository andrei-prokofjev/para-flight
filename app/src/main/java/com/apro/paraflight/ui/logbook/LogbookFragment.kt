package com.apro.paraflight.ui.logbook

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentLogbookBinding
import com.apro.paraflight.ui.common.viewBinding

class LogbookFragment : BaseFragment(R.layout.fragment_logbook) {

  private lateinit var component: LogbookScreenComponent
  private val binding by viewBinding { FragmentLogbookBinding.bind(it) }
  private val viewModel by viewModels<LogbookViewModel> { component.viewModelFactory() }

  private val logbookAdapter by lazy {
    LogbookAdapter {
      viewModel.startFlightReplay(it.flightPoints)
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      backImageButton.onClick { DI.appComponent.appRouter().exit() }

      logbooksRecyclerView.adapter = logbookAdapter

    }

    viewModel.logbookData.observe {
      logbookAdapter.items = it
    }


  }

  override fun getViewToApplyStatusBarMargin(root: View): Array<View> = arrayOf(binding.logbooksRecyclerView)

  override fun getViewToApplyNavigationBarMargin(root: View): Array<View> = arrayOf(binding.logbooksRecyclerView)

  companion object {
    fun create(component: LogbookScreenComponent) = LogbookFragment().apply {
      this.component = component
    }
  }


}