package com.apro.paraflight

import android.location.Location
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.apro.paraflight.di.AppComponent
import com.apro.paraflight.ui.flight.FlightScreenComponent
import com.apro.paraflight.ui.flight.FlightScreenViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber
import java.util.concurrent.CountDownLatch

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


  @Test
  fun useAppContext() {
    // Context of the app under test.
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    assertEquals("com.apro.paraflight", appContext.packageName)


//    scope.launch {
//      appComponent.flightRepository().locationChannel.send(location)
//    }
  }

  @Test
  fun flightInteractor() {

    val signal = CountDownLatch(1)


    val location = Location("test")
    location.speed = 5f
    location.altitude = 3.0

    val scope = CoroutineScope(CoroutineExceptionHandler { _, e -> Timber.e(e) })

    val c = FlightScreenComponent.create()
    val mViewModel: FlightScreenViewModel by lazy { c.viewModelFactory().create(FlightScreenViewModel::class.java) }


    val appContext = InstrumentationRegistry.getInstrumentation().targetContext


    val appComponent = AppComponent.create(appContext)

    // mViewModel.flightInteractor

    scope.launch {
      delay(3000)
      println(">>> send")
      appComponent.flightRepository().locationChannel.send(location)

    }

    var count = 0

    scope.launch {
      mViewModel.flightInteractor.updateLocationFlow().collect {
        println(">>> it: $it")
        if (count++ == 10)
          signal.countDown()
      }
    }
    signal.await()


  }


}