package com.apro.paraflight.ui.mapbox.customview

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.apro.paraflight.R

class MeterView constructor(context: Context, attr: AttributeSet) :
  ConstraintLayout(context, attr) {




  init {
    inflate(context, R.layout.view_meter, this)
  }
}