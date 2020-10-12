package com.apro.core_ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

abstract class BaseFragment : Fragment() {

  private val statusBarHeight by lazy { statusBarHeight() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    onStatusBarHeight(statusBarHeight)
    getViewToApplyStatusBarPadding(view).forEach { it.setPaddings(top = statusBarHeight) }
    getViewToApplyStatusBarMargin(view).forEach { it.setMargins(top = statusBarHeight) }
  }

  protected inline fun <T> LiveData<T>.observe(crossinline observer: (T) -> Unit) {
    observe(viewLifecycleOwner, { observer.invoke(it) })
  }

  protected open fun onStatusBarHeight(statusBarHeight: Int) {}

  protected open fun getViewToApplyStatusBarPadding(root: View): Array<View> = emptyArray()

  protected open fun getViewToApplyStatusBarMargin(root: View): Array<View> = emptyArray()

}