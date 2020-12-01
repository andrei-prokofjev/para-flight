package com.apro.paraflight.ui.flight


import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.apro.core.ui.onClick
import com.apro.paraflight.DI
import com.apro.paraflight.R


class AltitudeCalibrationDialog : DialogFragment(), DialogInterface.OnClickListener {


  private lateinit var calibrate: () -> Unit

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

    val v = inflater.inflate(R.layout.layout_dialog_altidude_calibration, null)
    val numberPicker = v.findViewById<NumberPicker>(R.id.offsetNumberPicker)
    numberPicker.minValue = 0
    numberPicker.maxValue = 40
    numberPicker.wrapSelectorWheel = false
    numberPicker.value = DI.appComponent.settingsPreferences().altitudeOffset
    numberPicker.setOnValueChangedListener { _, _, newVal -> DI.appComponent.settingsPreferences().altitudeOffset = newVal }

    numberPicker.setFormatter {
      val pref = if (it > 20) "+" else ""
      pref + (it - 20).toString() + " m"
    }
    try {
      numberPicker.javaClass.getDeclaredMethod("changeValueByOne", Boolean::class.javaPrimitiveType).apply {
        isAccessible = true
        invoke(numberPicker, true)
      }
    } catch (e: Exception) {
      // ignore
    }

    v.findViewById<Button>(R.id.autoButton).onClick {

      calibrate.invoke()
      dismissAllowingStateLoss()
    }

    return v
  }


  override fun onClick(dialog: DialogInterface, which: Int) {

  }


  override fun onDismiss(dialog: DialogInterface) {
    super.onDismiss(dialog)
    println(">>> dismiss$")
  }

  override fun onCancel(dialog: DialogInterface) {
    super.onCancel(dialog)
    println(">>> cancel$")
  }

  fun setCalibrateClick(onCalibrate: () -> Unit) {
    this.calibrate = onCalibrate
  }
}