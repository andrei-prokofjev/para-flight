import java.util.concurrent.TimeUnit

open class Unit(val name: String, private val ratio: Double) {
  fun convertToBaseUnit(amount: Double) = amount * ratio
  fun convertFromBaseUnit(amount: Double) = amount / ratio
}

open class Quantity<T : Unit>(val amount: Double, val unit: T) {
  fun convertTo(unit: T): Quantity<T> {
    val baseUnit = this.unit.convertToBaseUnit(amount)
    return Quantity(unit.convertFromBaseUnit(baseUnit), unit)
  }

  operator fun plus(quantity: Quantity<T>): Quantity<T> {
    val converted = quantity.convertTo(this.unit).amount
    val amount = this.amount + converted
    return Quantity(amount, this.unit)
  }

  operator fun minus(quantity: Quantity<T>): Quantity<T> {
    val converted = quantity.convertTo(this.unit).amount
    val amount = this.amount - converted
    return Quantity(amount, this.unit)
  }

  operator fun times(quantity: Quantity<T>): Quantity<T> {
    val converted = quantity.convertTo(this.unit).amount
    val amount = this.amount * converted
    return Quantity(amount, this.unit)
  }

  operator fun div(quantity: Quantity<T>): Quantity<T> {
    val converted = quantity.convertTo(this.unit).amount
    val amount = this.amount / converted
    return Quantity(amount, this.unit)
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

val Quantity<Distance>.miles get() = this.convertTo(Distance.Mile)
val Quantity<Distance>.kilometers get() = this.convertTo(Distance.Kilometer)
val Quantity<Distance>.meters get() = this.convertTo(Distance.Meter)
val Quantity<Distance>.centimeters get() = this.convertTo(Distance.Centimeter)
val Quantity<Distance>.millimeters get() = this.convertTo(Distance.Millimeter)

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

val Quantity<Time>.hours get() = this.convertTo(Time.Hour)
val Quantity<Time>.minutes get() = this.convertTo(Time.Minute)
val Quantity<Time>.seconds get() = this.convertTo(Time.Second)
val Quantity<Time>.milliseconds get() = this.convertTo(Time.Millisecond)

val Number.hours: Quantity<Time> get() = Quantity(this.toDouble(), Time.Hour)
val Number.minutes: Quantity<Time> get() = Quantity(this.toDouble(), Time.Minute)
val Number.seconds: Quantity<Time> get() = Quantity(this.toDouble(), Time.Second)
val Number.milliseconds: Quantity<Time> get() = Quantity(this.toDouble(), Time.Millisecond)

class Speed(name: String, ratio: Double) : Unit(name, ratio) {
  companion object Factory {
    val MetersPerSecond = Speed("m/s", 1.0)
    val KilometerPerHour = Speed("km/h", 1000 / TimeUnit.HOURS.toSeconds(1L).toDouble())
  }
}

val Quantity<Speed>.MetersPerSecond get() = this.convertTo(Speed.KilometerPerHour)
val Quantity<Speed>.KilometerPerHour get() = this.convertTo(Speed.MetersPerSecond)

val Number.metersPerSecond: Quantity<Speed> get() = Quantity(this.toDouble(), Speed.MetersPerSecond)
val Number.kilometersPerHour: Quantity<Speed>
  get() = Quantity(this.toDouble(),
    Speed.KilometerPerHour)

val Float.metersPerSecond: Quantity<Speed> get() = Quantity(this.toDouble(), Speed.MetersPerSecond)
val Float.kilometersPerHour: Quantity<Speed>
  get() = Quantity(this.toDouble(),
    Speed.KilometerPerHour)