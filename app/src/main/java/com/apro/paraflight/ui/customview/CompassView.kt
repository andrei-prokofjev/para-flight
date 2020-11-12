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
  private val off2 = dpToPx(120f)

  private val fontSize = dpToPx(20f)
  private val strokeWidth = dpToPx(2f)

  private val color = Color.parseColor("#CCFFFFFF")
  private val bgColor = Color.parseColor("#20000000")

  val path = Path().apply {
    addCircle(radius, radius, radius, Path.Direction.CW)
    addCircle(radius, radius, radius - off1, Path.Direction.CW)
    addCircle(radius, radius, radius - off2, Path.Direction.CW)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    paint.color = Color.TRANSPARENT
    canvas.drawPaint(paint)

    paint.style = Paint.Style.STROKE

    paint.strokeWidth = off2
    paint.color = bgColor
    canvas.drawCircle(width / 2f, height / 2f, radius - off2 / 2, paint)

    canvas.translate(width / 2f - radius, height / 2f - radius)

    paint.strokeWidth = strokeWidth
    paint.color = color
    canvas.drawPath(path, paint)

    paint.style = Paint.Style.FILL
    paint.color = color
    paint.isAntiAlias = true
    paint.typeface = Typeface.DEFAULT_BOLD
    paint.textAlign = Paint.Align.CENTER
    paint.textSize = fontSize

    paint.color = Color.RED
    canvas.drawTextOnPath("N", path, (Math.PI * radius).toFloat() / 2, fontSize, paint)
    paint.color = color
    canvas.drawTextOnPath("W", path, 0f, fontSize, paint)
    canvas.drawTextOnPath("E", path, 3 * (Math.PI * radius).toFloat() / 2, fontSize, paint)
    canvas.drawTextOnPath("S", path, -(Math.PI * radius).toFloat() / 2, fontSize, paint)

    paint.typeface = Typeface.SANS_SERIF
    canvas.drawTextOnPath("21", path, -(Math.PI * radius).toFloat() / 3, fontSize, paint)
    canvas.drawTextOnPath("33", path, (Math.PI * radius).toFloat() / 3, fontSize, paint)
    canvas.drawTextOnPath("30", path, (Math.PI * radius).toFloat() / 6, fontSize, paint)
    canvas.drawTextOnPath("24", path, -(Math.PI * radius).toFloat() / 6, fontSize, paint)

    canvas.drawTextOnPath("6", path, 5 * (Math.PI * radius).toFloat() / 6, fontSize, paint)
    canvas.drawTextOnPath("12", path, -5 * (Math.PI * radius).toFloat() / 6, fontSize, paint)

    canvas.drawTextOnPath("3", path, 2 * Math.PI.toFloat() * radius / 3, fontSize, paint)
    canvas.drawTextOnPath("15", path, -2 * Math.PI.toFloat() * radius / 3, fontSize, paint)

    paint.style = Paint.Style.STROKE



    for (a: Int in 10..350 step 10) {
      if (a % 3 != 0) {
        val sin = sin(Math.toRadians(a.toDouble()))
        val cos = cos(Math.toRadians(a.toDouble()))
        canvas.drawLine(
          radius + (sin * (radius - fontSize / 2)).toFloat(),
          radius + (cos * (radius - fontSize / 2)).toFloat(),
          radius + (sin * (radius - fontSize)).toFloat(),
          radius + (cos * (radius - fontSize)).toFloat(),
          paint)
      }
    }
  }
}