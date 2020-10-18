package com.apro.core_ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.*
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlin.math.abs

const val DEFAULT_THROTTLE_DURATION = 300L

inline fun <V : View> V.onClick(
  throttleDuration: Long = DEFAULT_THROTTLE_DURATION,
  crossinline listener: () -> Unit
): V {
  setOnClickListener(SafeClickListener(throttleDuration) { listener.invoke() })
  return this
}

inline fun <V : View> Array<V>.onClick(
  throttleDuration: Long = DEFAULT_THROTTLE_DURATION,
  crossinline listener: () -> Unit
) {
  forEach { it.setOnClickListener(SafeClickListener(throttleDuration) { listener.invoke() }) }
}

inline fun <V : View> V.onLongClick(crossinline listener: () -> Unit): V {
  setOnLongClickListener {
    listener.invoke()
    true
  }
  return this
}

fun throttleClicks(
  throttleDuration: Long = DEFAULT_THROTTLE_DURATION,
  vararg pairs: Pair<View, () -> Unit>
) {
  val doubleClickPreventer = DoubleClickPreventer(throttleDuration)
  pairs.forEach { (v, listener) ->
    v.setOnClickListener {
      if (!doubleClickPreventer.preventDoubleClick()) {
        listener.invoke()
      }
    }
  }
}

fun View.inflater(): LayoutInflater = LayoutInflater.from(context)
fun ViewGroup.inflate(@LayoutRes layoutId: Int): View = LayoutInflater.from(context).inflate(layoutId, this, false)
fun Fragment.inflate(@LayoutRes layoutId: Int): View = LayoutInflater.from(requireContext()).inflate(layoutId, null, false)

val View.topMargin get() = (layoutParams as ViewGroup.MarginLayoutParams).topMargin
val View.bottomMargin get() = (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin
val View.leftMargin get() = (layoutParams as ViewGroup.MarginLayoutParams).leftMargin
val View.rightMargin get() = (layoutParams as ViewGroup.MarginLayoutParams).rightMargin

fun View.setMargins(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
  val marginLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
  marginLayoutParams.setMargins(
    left ?: marginLayoutParams.leftMargin,
    top ?: marginLayoutParams.topMargin,
    right ?: marginLayoutParams.rightMargin,
    bottom ?: marginLayoutParams.bottomMargin
  )
  this.layoutParams = marginLayoutParams
}

fun View.setPaddings(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
  setPadding(
    left ?: paddingLeft,
    top ?: paddingTop,
    right ?: paddingRight,
    bottom ?: paddingBottom
  )
}

fun View.setSize(width: Int? = null, height: Int? = null) {
  width?.let { layoutParams.width = it }
  height?.let { layoutParams.height = it }
  requestLayout()
}

fun View.dpToPx(dp: Float): Float {
  val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val display = wm.defaultDisplay
  val metrics = DisplayMetrics()
  display.getMetrics(metrics)
  return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics)
}

fun View.pxToDp(px: Int): Float {
  val metrics = context.resources.displayMetrics
  return px.toFloat() / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun EditText.showKeyboard(delay: Long = 0L) {
  if (delay == 0L) {
    showKeyboardImmediately()
  } else {
    postDelayed({ showKeyboardImmediately() }, delay)
  }
}

private fun EditText.showKeyboardImmediately() {
  requestFocus()
  val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  inputManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.getColor(@ColorRes resColor: Int): Int {
  return ContextCompat.getColor(context, resColor)
}

fun Fragment.dpToPx(dp: Float): Int {
  val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val display = wm.defaultDisplay
  val metrics = DisplayMetrics()
  display.getMetrics(metrics)
  return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics).toInt()
}

fun Fragment.pxToDp(px: Int): Float {
  val metrics = requireContext().resources.displayMetrics
  return px.toFloat() / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun Context.dpToPx(dp: Float): Int {
  val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val display = wm.defaultDisplay
  val metrics = DisplayMetrics()
  display.getMetrics(metrics)
  return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics).toInt()
}

fun Fragment.statusBarHeight(): Int {
  return activity?.statusBarHeight() ?: 0
}

fun Activity.statusBarHeight(): Int {
  val rectangle = Rect()
  val window = window
  window.decorView.getWindowVisibleDisplayFrame(rectangle)
  return if (rectangle.top > 0) rectangle.top else statusBarHeightFromResources()
}

fun Activity.statusBarHeightFromResources(): Int {
  val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
  if (resourceId > 0) {
    return resources.getDimensionPixelSize(resourceId)
  }
  return 0
}

fun Context.navBarHeight(isLandscapeMode: Boolean = false): Int {
  if (hasSoftBottomBar(isLandscapeMode)) {
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    if (resourceId > 0) {
      return resources.getDimensionPixelSize(resourceId)
    }
  }
  return 0
}

fun Context.navBarDeviceHeight(): Int {
  val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
  if (resourceId > 0) {
    return resources.getDimensionPixelSize(resourceId)
  }
  return 0
}

fun Fragment.navBarHeight(): Int = requireContext().navBarHeight()


fun Fragment.toast(@StringRes textId: Int, long: Boolean = false, gravity: Int? = null) {
  toast(getString(textId), long, gravity)
}

fun Fragment.toast(text: String, long: Boolean = false, gravity: Int? = null) {
  activity?.runOnUiThread {
    Toast.makeText(requireContext(), text, if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
      .apply {
        gravity?.let { setGravity(it, 0, 0) }
        show()
      }
  }
}

fun Activity.toast(text: String, long: Boolean = false, gravity: Int? = null) {
  runOnUiThread {
    Toast.makeText(baseContext, text, if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
      .apply {
        gravity?.let { setGravity(it, 0, 0) }
        show()
      }
  }
}

fun Context.hasSoftBottomBar(isLandscapeMode: Boolean = false): Boolean {
  val bottomBarHeight = dpToPx(48f) // 48 is bottom bar height on Nexus 6 at least
  val screenSize = screenSize(this)
  val fullSize = fullScreenSize(this)
  return if (!isLandscapeMode) fullSize.y - screenSize.y >= bottomBarHeight
  else fullSize.x - screenSize.x >= bottomBarHeight
}

fun screenSize(context: Context): Point {
  val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val display = wm.defaultDisplay
  val size = Point()
  display.getSize(size)
  return size
}

fun fullScreenSize(context: Context): Point {
  // fix for cases when bottom of the screen has soft action bar that 'steals' tiny amount of parent height
  val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val display = wm.defaultDisplay
  val size = Point()
  display.getRealSize(size)
  return size
}

fun View.drawBitmap(x: Int = 0, y: Int = 0, w: Int = width, h: Int = height): Bitmap {
  val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
  val canvas = Canvas(bitmap)
  draw(canvas)
  return Bitmap.createBitmap(bitmap, x, y, w, h)
}

fun concatBitmaps(width: Int, height: Int, vararg views: View): Bitmap {
  val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
  val canvas = Canvas(bitmap)
  views.forEach { it.draw(canvas) }
  return bitmap
}

fun concatBitmaps(width: Int, height: Int, vararg bitmaps: Bitmap): Bitmap {
  val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
  val canvas = Canvas(bitmap)
  bitmaps.forEach { canvas.drawBitmap(it, Matrix(), null) }
  return bitmap
}

fun createMaskPath(size: Int): Path = createMaskPath(size, 0)

fun createMaskPath(size: Int, padding: Int): Path {
  //Formula: (|x|)^3 + (|y|)^3 = radius^3
  val radius = size / 2 - padding
  val radiusToPow = radius * radius * radius.toDouble()
  val path = Path()
  path.moveTo(-radius.toFloat(), 0f)
  for (x in -radius + 1..radius) {
    path.lineTo(x.toFloat(), Math.cbrt(radiusToPow - abs(x * x * x)).toFloat())
  }
  for (x in radius downTo -radius) {
    path.lineTo(x.toFloat(), (-Math.cbrt(radiusToPow - abs(x * x * x))).toFloat())
  }
  path.close()
  val matrix = Matrix()
  matrix.postTranslate(size / 2.toFloat(), size / 2.toFloat())
  path.transform(matrix)
  return path
}

fun TextView.textColor(id: Int) {
  setTextColor(ContextCompat.getColor(context, id))
}

fun Context.getScreenHeight(): Int {
  val size = Point()
  this.unwrap().windowManager.defaultDisplay.getSize(size)
  return size.y
}

fun Context.getScreenWidth(): Int {
  val size = Point()
  this.unwrap().windowManager.defaultDisplay.getSize(size)
  return size.x
}

fun View.setNewHeightPx(newHeight: Int) {
  layoutParams.height = newHeight
}

fun Context.unwrap(): Activity {
  var unwrappedContext = this
  while (unwrappedContext !is Activity && unwrappedContext is ContextWrapper) {
    unwrappedContext = unwrappedContext.baseContext
  }
  return unwrappedContext as Activity
}

