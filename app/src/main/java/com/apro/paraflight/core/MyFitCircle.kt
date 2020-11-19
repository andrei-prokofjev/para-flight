package com.apro.paraflight.core

import android.graphics.PointF
import kotlin.math.abs
import kotlin.math.sqrt


object MyFitCircle {
  fun center(points: Array<PointF>): PointF {
    var sumX = 0f
    var sumY = 0f

    points.forEach {
      sumX += it.x
      sumY += it.y
    }

    return PointF().apply {
      x = sumX / points.size
      y = sumY / points.size
    }
  }

  fun taubinNewton(points: Array<PointF>): WindVector {
    require(points.size >= 3) { "Too few points" }
    val centroid = center(points)
    var mxx = 0f
    var myy = 0f
    var mxy = 0f
    var mxz = 0f
    var myz = 0f
    var mzz = 0f
    for (point in points) {
      val xi = point.x - centroid.x
      val yi = point.y - centroid.y
      val zi = xi * xi + yi * yi
      mxy += xi * yi
      mxx += xi * xi
      myy += yi * yi
      mxz += xi * zi
      myz += yi * zi
      mzz += zi * zi
    }
    mxx /= points.size
    myy /= points.size
    mxy /= points.size
    mxz /= points.size
    myz /= points.size
    mzz /= points.size
    val mz = mxx + myy
    val covXy = mxx * myy - mxy * mxy
    val a3 = 4 * mz
    val a2 = -3 * mz * mz - mzz
    val a1 = mzz * mz + 4 * covXy * mz - mxz * mxz - myz * myz - mz * mz * mz
    val a0 = mxz * mxz * myy + myz * myz * mxx - mzz * covXy - 2 * mxz * myz * mxy + mz * mz * covXy
    val a22 = a2 + a2
    val a33 = a3 + a3 + a3
    var xnew = 0f
    var ynew = 1e+20f
    val epsilon = 1e-12f
    val iterMax = 20f
    var iter = 0
    while (iter < iterMax) {
      val yold = ynew
      ynew = a0 + xnew * (a1 + xnew * (a2 + xnew * a3))
      if (abs(ynew) > abs(yold)) {
        println("Newton-Taubin goes wrong direction: |ynew| > |yold|")
        xnew = 0f
        break
      }
      val dy = a1 + xnew * (a22 + xnew * a33)
      val xold = xnew
      xnew = xold - ynew / dy
      if (abs((xnew - xold) / xnew) < epsilon) {
        break
      }
      if (iter >= iterMax) {
        println("Newton-Taubin will not converge")
        xnew = 0f
      }
      if (xnew < 0) {
        println("Newton-Taubin negative root: x = $xnew")
        xnew = 0f
      }
      iter++
    }
    val det = xnew * xnew - xnew * mz + covXy
    val x = (mxz * (myy - xnew) - myz * mxy) / (det * 2)
    val y = (myz * (mxx - xnew) - mxz * mxy) / (det * 2)

    return WindVector(x + centroid.x, y + centroid.y, sqrt(x * x + y * y + mz))
  }
}