package com.apro.paraflight.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.apro.paraflight.R
import com.apro.paraflight.databinding.ViewMeterBinding


class MeterView constructor(context: Context, attr: AttributeSet?) :
  ConstraintLayout(context, attr) {

  private val binding: ViewMeterBinding = ViewMeterBinding.inflate(LayoutInflater.from(context), this, true)

  private var name: String? = null
    set(value) {
      binding.nameTextView.text = value
      field = value
    }

  private var title: String? = null
    set(value) {
      binding.titleTextView.text = value
      field = value
    }

  var amount: String? = null
    set(value) {
      binding.amountTextView.text = value
      field = value
    }

  var info: String? = null
    set(value) {
      binding.infoTextView.text = value
      field = value
    }

  var unit: String? = null
    set(value) {
      binding.unitTextView.text = value
      field = value
    }

  init {
    val a = context.obtainStyledAttributes(attr, R.styleable.MeterView)
    name = a.getString(R.styleable.MeterView_mv_name)
    title = a.getString(R.styleable.MeterView_mv_title)
    unit = a.getString(R.styleable.MeterView_mv_unit)
    info = a.getString(R.styleable.MeterView_mv_info)
    a.recycle()
  }
}