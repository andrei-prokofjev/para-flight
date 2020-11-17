package com.apro.paraflight

import android.graphics.PointF
import androidx.test.ext.junit.runners.AndroidJUnit4
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

    assertEquals(center.x, 11.25f)
    assertEquals(center.y, 36.25f)


  }

  @Test
  fun taubinNewton() {
    val points = arrayOf(
      PointF(10f, 30f),
      PointF(5f, 30f),
      PointF(10f, 40f),
      PointF(20f, 45f),
    )
    val a = MyFitCircle.taubinNewton(points)


    println(">>> a $a")


  }


}
