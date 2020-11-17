package com.apro.paraflight

import android.graphics.PointF
import android.location.Location
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.apro.paraflight.di.AppComponent
import com.apro.paraflight.ui.flight.FlightInteractor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.CountDownLatch

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LocationEngineInstrumentedTest {

  private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
  private val appComponent = AppComponent.create(appContext)

  private var scope: CoroutineScope? = null


  @Before
  fun init() {
    clear()
    scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(">>> !!! $e") })
  }

  @Test
  fun useAppContext() {
    // Context of the app under test.
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    assertEquals("com.apro.paraflight", appContext.packageName)

    val p = PointF(10f, 30f)

    println(">>>> $p")
  }

  @Test
  fun lastLocation() {
//    val signal = CountDownLatch(1)
//    val engine = appComponent.locationEngine()
//    engine.getLastLocation()
//    scope?.launch(Dispatchers.IO) {
//      engine.lastLocationFlow().collect {
//        assertNotNull(it)
//        signal.countDown()
//      }
//    }
//
//    assertEquals(signal.await(DEFAULT_MAX_WAIT_TIME, TimeUnit.MILLISECONDS), true)
  }

  @Test
  fun updateLocation() {
    val signal = CountDownLatch(1)

//    val c = FlightScreenComponent.create()
//    val viewModel: FlightScreenViewModel by lazy { c.viewModelFactory().create(FlightScreenViewModel::class.java) }
//
//
//    val takeOffSpeed = DI.preferencesApi.settings().takeOffSpeed
//    val takeOffAltDiff = DI.preferencesApi.settings().takeOffAltDiff


    signal.await()
  }

  @After
  fun clear() {
    scope?.cancel()
  }

  private fun readCsv(path: String): List<TestFlightModel> {

    val assets = appContext.assets

    val flight = mutableListOf<TestFlightModel>()

    try {
      val csvStream = assets.open(path)
      val csvStreamReader = InputStreamReader(csvStream)
      val lines = csvStreamReader.readLines()
      lines.subList(1, lines.size).forEach {
        val data = it.split(",")
        val location = Location("mock")
        location.speed = data[1].toFloat()
        location.altitude = data[2].toDouble()
        flight.add(TestFlightModel(location, FlightInteractor.FlightState.valueOf(data[3])))
      }

    } catch (e: IOException) {
      println(">>> !!! $e")
    }

    val list = emptyList<Location>()
    return flight
  }
}

data class TestFlightModel(val location: Location, val state: FlightInteractor.FlightState)