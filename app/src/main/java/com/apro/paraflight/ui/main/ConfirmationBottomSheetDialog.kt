package com.apro.paraflight.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apro.core.ui.BaseBottomSheetDialogFragment
import com.apro.paraflight.databinding.FragmentCommonConfiramtionBinding

class ConfirmationBottomSheetDialog : BaseBottomSheetDialogFragment() {


  private lateinit var binding: FragmentCommonConfiramtionBinding

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = FragmentCommonConfiramtionBinding.inflate(inflater)
    return binding.root
  }

  companion object {
    fun create() = ConfirmationBottomSheetDialog()
  }
}