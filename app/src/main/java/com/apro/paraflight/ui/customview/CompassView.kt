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

  private val rec1 = RectF(0f, 0f, diameter, diameter)
  private val rec2 = RectF(70f, 70f, diameter - 70, diameter - 70)

  val path = Path().apply {
    addArc(rec1, 0f, 360f)
    addArc(rec2, 0f, 360f)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    paint.color = Color.TRANSPARENT
    canvas.drawPaint(paint)

    canvas.translate(width / 2f - radius, height / 2f - radius)

    paint.style = Paint.Style.STROKE
    paint.strokeWidth = dpToPx(2f)
    paint.color = Color.parseColor("#ffffff")
    canvas.drawPath(path, paint)

    paint.style = Paint.Style.FILL
    paint.color = Color.parseColor("#ffff00")
    paint.isAntiAlias = true
    paint.typeface = Typeface.SANS_SERIF
    paint.textAlign = Paint.Align.CENTER
    paint.textSize = dpToPx(24f)

    canvas.drawTextOnPath("N", path, (Math.PI * diameter).toFloat() / 4f, dpToPx(24f), paint)
    canvas.drawTextOnPath("E", path, (Math.PI * diameter).toFloat() / 2f, dpToPx(24f), paint)
    canvas.drawTextOnPath("S", path, -(Math.PI * diameter).toFloat() / 4f, dpToPx(24f), paint)
    canvas.drawTextOnPath("W", path, 0f, dpToPx(24f), paint)


  }
}