package com.apro.paraflight.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.apro.paraflight.R
import com.apro.paraflight.databinding.ViewMeterBinding

class MeterView constructor(context: Context, attr: AttributeSet?) :
  ConstraintLayout(context, attr) {

  private val binding: ViewMeterBinding =
    ViewMeterBinding.inflate(LayoutInflater.from(context), this, true)

  var title: String? = null
    set(value) {
      binding.titleTextView.text = value
      field = value
    }

  var amount: String? = null
    set(value) {
      binding.amountTextView.text = value
      field = value
    }

  var unit: String? = null
    set(value) {
      binding.unitTextView.text = value
      field = value
    }

  init {
    val a = context.obtainStyledAttributes(attr, R.styleable.MeterView)
    title = a.getString(R.styleable.MeterView_mv_title)
    unit = a.getString(R.styleable.MeterView_mv_unit)
    a.recycle()
  }
}