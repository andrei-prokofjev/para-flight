package com.apro.core.util

import java.util.concurrent.TimeUnit

open class Unit(val name: String, private val ratio: Double) {
  fun convertToBaseUnit(amount: Double) = amount * ratio
  fun convertFromBaseUnit(amount: Double) = amount / ratio
}

open class Quantity<T : Unit>(val amount: Double, private val unit: T) {
  fun convertTo(unit: T): Quantity<T> {
    val baseUnit = this.unit.convertToBaseUnit(amount)
    return Quantity(unit.convertFromBaseUnit(baseUnit), unit)
  }
}

class Distance(name: String, ratio: Double) : Unit(name, ratio) {
  companion object Factory {
    val Mile = Distance("ml", 1.60934 * 1000.0)
    val Kilometer = Distance("km", 1000.0)
    val Meter = Distance("m", 1.0)
    val Centimeter = Distance("cm", 0.01)
    val Millimeter = Distance("mm", 0.001)
  }
}

val Quantity<Distance>.miles get() = convertTo(Distance.Mile)
val Quantity<Distance>.kilometers get() = convertTo(Distance.Kilometer)
val Quantity<Distance>.meters get() = convertTo(Distance.Meter)
val Quantity<Distance>.centimeters get() = convertTo(Distance.Centimeter)
val Quantity<Distance>.millimeters get() = convertTo(Distance.Millimeter)

val Number.meters: Quantity<Distance> get() = Quantity(this.toDouble(), Distance.Meter)
val Number.kilometers: Quantity<Distance> get() = Quantity(this.toDouble(), Distance.Kilometer)
val Number.miles: Quantity<Distance> get() = Quantity(this.toDouble(), Distance.Mile)
val Number.centimeter: Quantity<Distance> get() = Quantity(this.toDouble(), Distance.Centimeter)
val Number.millimeter: Quantity<Distance> get() = Quantity(this.toDouble(), Distance.Millimeter)

class Time(name: String, ratio: Double) : Unit(name, ratio) {
  companion object Factory {
    val Hour = Time("h", TimeUnit.HOURS.toMillis(1L).toDouble())
    val Minute = Time("m", TimeUnit.MINUTES.toMillis(1L).toDouble())
    val Second = Time("s", TimeUnit.SECONDS.toMillis(1L).toDouble())
    val Millisecond = Time("ms", 1.0)
  }
}

val Quantity<Time>.hours get() = convertTo(Time.Hour)
val Quantity<Time>.minutes get() = convertTo(Time.Minute)
val Quantity<Time>.seconds get() = convertTo(Time.Second)
val Quantity<Time>.milliseconds get() = convertTo(Time.Millisecond)

val Number.hours: Quantity<Time> get() = Quantity(toDouble(), Time.Hour)
val Number.minutes: Quantity<Time> get() = Quantity(toDouble(), Time.Minute)
val Number.seconds: Quantity<Time> get() = Quantity(toDouble(), Time.Second)
val Number.milliseconds: Quantity<Time> get() = Quantity(toDouble(), Time.Millisecond)

enum class SpeedUnit(val n: String, val ratio: Double) {
  METER_PER_SECOND("m/s", 1.0),
  KM_PER_HOUR("km/h", 1000 / TimeUnit.HOURS.toSeconds(1L).toDouble()),
}

class Speed(unit: SpeedUnit) : Unit(unit.n, unit.ratio) {
  companion object Factory {
    val MetersPerSecond = Speed(SpeedUnit.METER_PER_SECOND)
    val KilometerPerHour = Speed(SpeedUnit.KM_PER_HOUR)
  }
}

val Quantity<Speed>.MetersPerSecond get() = convertTo(Speed.KilometerPerHour)
val Quantity<Speed>.KilometerPerHour get() = convertTo(Speed.MetersPerSecond)

val Number.metersPerSecond: Quantity<Speed> get() = Quantity(toDouble(), Speed.MetersPerSecond)
val Number.kilometersPerHour: Quantity<Speed>
  get() = Quantity(this.toDouble(),
    Speed.KilometerPerHour)

val Float.metersPerSecond: Quantity<Speed> get() = Quantity(toDouble(), Speed.MetersPerSecond)
val Float.kilometersPerHour: Quantity<Speed>
  get() = Quantity(this.toDouble(),
    Speed.KilometerPerHour)