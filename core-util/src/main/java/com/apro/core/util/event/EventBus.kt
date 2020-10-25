package com.apro.core.util.event

import androidx.annotation.AnyThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.reflect.KClass

object EventBus {
  object EventMock : Event

  private val channelFlow = MutableStateFlow<Event>(EventMock)

  @AnyThread
  fun send(event: Event, delayInMillis: Long = 0L) {
    if (delayInMillis > 0L) {
      GlobalScope.launch(Dispatchers.Default) {
        delay(delayInMillis)
        Timber.d(event.toString())
        channelFlow.value = event
      }
    } else {
      Timber.d(event.toString())
      channelFlow.value = event
    }
  }

  @Suppress("UNCHECKED_CAST")
  fun <T : Event> observeChannel(type: KClass<T>) = channelFlow
    .drop(1) // kinda rendezvous BroadCastChannel for buffer size == 1 without suspending receivers
    .filter { it::class == type }
    .map { it as T }
}
