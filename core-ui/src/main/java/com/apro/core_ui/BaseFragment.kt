package com.apro.core_ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

  private val statusBarHeight by lazy { statusBarHeight() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    onStatusBarHeight(statusBarHeight)
    getViewToApplyStatusBarPadding(view).forEach { it.setPaddings(top = statusBarHeight) }
    getViewToApplyStatusBarMargin(view).forEach { it.setMargins(top = statusBarHeight) }
  }

  protected open fun getViewToApplyStatusBarPadding(root: View): Array<View> = emptyArray()

  protected open fun onStatusBarHeight(statusBarHeight: Int) {}

  protected open fun getViewToApplyStatusBarMargin(root: View): Array<View> = emptyArray()

}