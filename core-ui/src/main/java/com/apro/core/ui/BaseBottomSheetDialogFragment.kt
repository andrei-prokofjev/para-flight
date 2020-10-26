package com.apro.core.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import timber.log.Timber

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {
  open val peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
      override fun onGlobalLayout() {
        val bottomSheet = dialog?.findViewById(R.id.design_bottom_sheet) as? FrameLayout ?: return
        bottomSheet.background = ColorDrawable(ContextCompat.getColor(requireContext(), android.R.color.transparent))
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.peekHeight = peekHeight
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.skipCollapsed = true
        view.viewTreeObserver.removeOnGlobalLayoutListener(this)
      }
    })
  }

  override fun onDestroyView() {
    val viewGroup = (view as? ViewGroup)
    viewGroup?.let { releaseAdaptersRecursively(it) }
    super.onDestroyView()
  }

  private fun releaseAdaptersRecursively(viewGroup: ViewGroup) {
    for (i in 0 until (viewGroup.childCount)) {
      when (val child = viewGroup.getChildAt(i)) {
        is RecyclerView -> {
          (viewGroup.getChildAt(i) as? RecyclerView)?.adapter = null
          Timber.d("Bottom sheet recycler adapter released: ${viewGroup.getChildAt(i)}")
        }
        is ViewGroup -> releaseAdaptersRecursively(child)
      }
    }
  }

  protected inline fun <T> LiveData<T>.observe(crossinline observer: (T) -> Unit) {
    observe(viewLifecycleOwner, { observer.invoke(it) })
  }
}