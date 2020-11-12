package com.apro.paraflight.ui.customview

import android.content.Context
import android.graphics.*

import android.util.AttributeSet
import android.view.View
import com.apro.core.ui.dpToPx
import kotlin.math.cos
import kotlin.math.sin

class CompassView(context: Context, attr: AttributeSet?) : View(context, attr) {

  var paint = Paint()

  private val diameter = dpToPx(300f)
  private val radius = diameter / 2

  private val off1 = dpToPx(26f)
  private val off2 = dpToPx(100f)

  private val fontSize = dpToPx(20f)
  private val strokeWidth = dpToPx(1f)

  val path = Path().apply {
    addArc(RectF(0f, 0f, diameter, diameter), 0f, 360f)
    addArc(RectF(off1, off1, diameter - off1, diameter - off1), 0f, 360f)
    addArc(RectF(off2, off2, diameter - off2, diameter - off2), 0f, 360f)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    paint.color = Color.TRANSPARENT
    canvas.drawPaint(paint)

    canvas.translate(width / 2f - radius, height / 2f - radius)

    paint.style = Paint.Style.STROKE
    paint.strokeWidth = strokeWidth
    paint.color = Color.parseColor("#AAFFFFFF")
    canvas.drawPath(path, paint)

    paint.style = Paint.Style.FILL
    paint.color = Color.parseColor("#AAFFFF00")
    paint.isAntiAlias = true
    paint.typeface = Typeface.SANS_SERIF
    paint.textAlign = Paint.Align.CENTER
    paint.textSize = fontSize

//    canvas.drawTextOnPath("N", path, (Math.PI * diameter).toFloat() / 4f, fontSize, paint)
    canvas.drawTextOnPath("E", path, (Math.PI * radius).toFloat(), fontSize, paint)
    canvas.drawTextOnPath("S", path, -(Math.PI * radius).toFloat() / 2, fontSize, paint)
    canvas.drawTextOnPath("W", path, 0f, fontSize, paint)

    paint.style = Paint.Style.STROKE
    paint.color = Color.parseColor("#AAFFFFFF")

    for (a: Int in 0..360 step 10) {
      if (a == 90) {

      } else {
        val x1 = radius + (sin(Math.toRadians(a.toDouble())) * (radius - fontSize / 2)).toFloat()
        val x2 = radius + (sin(Math.toRadians(a.toDouble())) * (radius - fontSize)).toFloat()
        val y1 = radius + (cos(Math.toRadians(a.toDouble())) * (radius - fontSize / 2)).toFloat()
        val y2 = radius + (cos(Math.toRadians(a.toDouble())) * (radius - fontSize)).toFloat()
        if (a % 3 == 0) {
          canvas.drawTextOnPath(a.toString(), path, (Math.PI * radius).toFloat() / 0, fontSize, paint)
        } else {

          canvas.drawLine(x1, y1, x2, y2, paint)
        }
      }
    }


  }
}