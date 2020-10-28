package com.apro.core.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

  private val statusBarHeight by lazy { statusBarHeight() }

  private val navBarHeight by lazy { navBarHeight() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    onStatusBarHeight(statusBarHeight)
    getViewToApplyStatusBarPadding(view).forEach { it.setPaddings(top = statusBarHeight) }
    getViewToApplyStatusBarMargin(view).forEach { it.setMargins(top = statusBarHeight) }
    getViewToApplyNavigationBarMargin(view).forEach { it.setMargins(bottom = navBarHeight) }
    getViewToApplyNavigationBarPadding(view).forEach { it.setPaddings(bottom = navBarHeight) }
  }

  protected inline fun <T> LiveData<T>.observe(crossinline observer: (T) -> Unit) {
    observe(viewLifecycleOwner, { observer.invoke(it) })
  }

  protected open fun onStatusBarHeight(statusBarHeight: Int) {}

  protected open fun getViewToApplyStatusBarPadding(root: View): Array<View> = emptyArray()

  protected open fun getViewToApplyStatusBarMargin(root: View): Array<View> = emptyArray()

  protected open fun getViewToApplyNavigationBarPadding(root: View): Array<View> = emptyArray()
  protected open fun getViewToApplyNavigationBarMargin(root: View): Array<View> = emptyArray()

}