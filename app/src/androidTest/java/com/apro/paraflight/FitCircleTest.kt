package com.apro.paraflight

import android.graphics.PointF
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.apro.paraflight.core.Centroid
import com.apro.paraflight.core.FitCircle
import com.apro.paraflight.core.MyFitCircle
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FitCircleTest {


  @Test
  fun centerTest() {
    val points = arrayOf(
      PointF(10f, 30f),
      PointF(5f, 30f),
      PointF(10f, 40f),
      PointF(20f, 45f),
    )

    val center = MyFitCircle.center(points)

    val pts = Array(points.size) { i -> doubleArrayOf(points[i].x.toDouble(), points[i].y.toDouble()) }
    val c = Centroid.getCentroid(pts)

    assertEquals(center.x, c[0].toFloat())
    assertEquals(center.y, c[1].toFloat())
  }

  @Test
  fun taubinNewton() {
    val points = arrayOf(
      PointF(10f, 30f),
      PointF(5f, 30f),
      PointF(10f, 40f),
      PointF(20f, 45f),
    )
    val vector = MyFitCircle.taubinNewton(points)

    val pts = Array(points.size) { i -> doubleArrayOf(points[i].x.toDouble(), points[i].y.toDouble()) }
    val v = FitCircle.taubinNewton(pts)

    println(">>> x " + v[0])
    println(">>> y " + v[1])
    println(">>> r " + v[2])


    assertEquals(vector.x, 19.473793f)
    assertEquals(vector.y, 32.1224f)
    assertEquals(vector.radius, 12.511709f)


  }


}
