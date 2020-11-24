package com.apro.paraflight.ui.about

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import com.apro.core.location.engine.impl.MapboxLocationEngine
import com.apro.core.ui.BaseFragment
import com.apro.core.ui.onClick
import com.apro.core.ui.toast
import com.apro.paraflight.BuildConfig
import com.apro.paraflight.DI
import com.apro.paraflight.R
import com.apro.paraflight.databinding.FragmentAboutBinding
import com.apro.paraflight.ui.common.viewBinding
import kotlinx.coroutines.*


class AboutFragment : BaseFragment(R.layout.fragment_about) {

  private lateinit var component: AboutScreenComponent
  private val binding by viewBinding { FragmentAboutBinding.bind(it) }
  private val viewModel by viewModels<AboutScreenViewModel> { component.viewModelFactory() }

  var sensorManager: SensorManager? = null
  var pressureSensor: Sensor? = null

  private val sensorEventListener = object : SensorEventListener {
    override fun onSensorChanged(sensorEvent: SensorEvent) {
      binding.pressureSensorTextView.text = getString(R.string.pressure_sensor_s, String.format("%.2f", sensorEvent.values[0]))
    }

    override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      clientVersionTextView.text = resources.getString(R.string.client_version_s, BuildConfig.VERSION_NAME)
      clientNameTextView.text = resources.getString(R.string.user_name_s, DI.preferencesApi.userProfile().nickname ?: "-")

      GlobalScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, e ->
        e.message?.let { toast(it, true) }
      }) {

        val engine = MapboxLocationEngine(this@AboutFragment.requireContext())
        val currentLocation = engine.lastLocation()

        DI.networkComponent.whetherApi().weatherByLocation(
          lat = currentLocation.latitude,
          lon = currentLocation.longitude
        ).apply {
          withContext(Dispatchers.Main) {
            locationNameTextView.text = resources.getString(R.string.location_name_s, this@apply.name)
            visibilityTextView.text = resources.getString(R.string.visibility_s, this@apply.visibility)
            temperatureTextView.text = resources.getString(R.string.temperature_s, this@apply.main.temp.toString())
            pressureTextView.text = resources.getString(R.string.pressure_s, this@apply.main.pressure.toString())
          }
        }
      }

      backImageButton.onClick { viewModel.onBackClick() }


      sensorManager = getSystemService(requireContext(), SensorManager::class.java)
      pressureSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_PRESSURE)

      if (pressureSensor == null) {
        binding.pressureSensorTextView.text = getString(R.string.pressure_sensor_s, "unavailable :(")
      }
    }
  }

  override fun onResume() {
    super.onResume()

    pressureSensor?.let {
      sensorManager?.registerListener(sensorEventListener, it, SensorManager.SENSOR_DELAY_NORMAL)
    }

  }

  override fun onPause() {
    super.onPause()
    pressureSensor?.let {
      sensorManager?.unregisterListener(sensorEventListener)
    }
  }

  companion object {
    fun create(component: AboutScreenComponent) = AboutFragment().apply {
      this.component = component
    }
  }

}