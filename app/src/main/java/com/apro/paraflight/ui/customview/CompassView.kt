package com.apro.paraflight.ui.customview

import android.content.Context
import android.graphics.*

import android.util.AttributeSet
import android.view.View
import com.apro.core.ui.dpToPx

class CompassView(context: Context, attr: AttributeSet?) : View(context, attr) {

  var paint = Paint()

  private val diameter = dpToPx(300f)
  private val radius = diameter / 2

  private val rec = RectF(0f, 0f, diameter, diameter)

  val path = Path().apply {
    addArc(rec, 0f, 360f)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    paint.color = Color.TRANSPARENT
    canvas.drawPaint(paint)

    canvas.translate(width / 2f - radius, height / 2f - radius)

    paint.style = Paint.Style.STROKE
    paint.strokeWidth = dpToPx(2f)
    paint.color = Color.parseColor("#CD5C5C")
    canvas.drawPath(path, paint)

    paint.style = Paint.Style.FILL
    paint.color = Color.parseColor("#ffff00")
    paint.isAntiAlias = true
    paint.typeface = Typeface.SANS_SERIF
    paint.textAlign = Paint.Align.CENTER
    paint.textSize = dpToPx(24f)

    canvas.drawTextOnPath("W", path, (Math.PI * diameter).toFloat() / 4f, dpToPx(24f), paint)
    canvas.drawTextOnPath("E", path, (Math.PI * diameter).toFloat() / 2f, dpToPx(24f), paint)
    canvas.drawTextOnPath("N", path, -(Math.PI * diameter).toFloat() / 4f, dpToPx(24f), paint)
    canvas.drawTextOnPath("S", path, 0f, dpToPx(24f), paint)


  }
}