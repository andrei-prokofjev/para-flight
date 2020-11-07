package com.apro.paraflight.events

import android.location.Location
import com.apro.core.util.event.Event

class UpdateLocationEvent(val location: Location) : Event