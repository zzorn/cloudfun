package org.cloudfun.util
import scala.math._

/**
 * A range from some value to some other.
 */
trait ValueRange {

  def min: Double
  def max: Double
  def average = (min + max) / 2
  def contains(v: Double) = v >= min && v <= max
  def contains(other: ValueRange) = max >= other.max && min <= other.min
  def intersects(other: ValueRange) = min <= other.max && max >= other.min

}

case class Around(value: Double, variance: Double) extends ValueRange {
  if (variance < 0 ) throw new IllegalArgumentException("Variance should not be negative")

  val min = value - variance
  val max = value + variance
}

case class Between(min: Double, max: Double) extends ValueRange {
  if (min > max) throw new IllegalArgumentException("Min should be smaller than max")
}

case class Above(min: Double) extends ValueRange {
  val max = Double.PositiveInfinity
}

case class Below(max: Double) extends ValueRange {
  val min = Double.NegativeInfinity
}

case object AllValues extends ValueRange {
  val min = Double.NegativeInfinity
  val max = Double.PositiveInfinity
}

case object NoValues extends ValueRange {
  val min = Double.NaN
  val max = Double.NaN
  override def average = Double.NaN
  override def intersects(other: ValueRange) = false
  override def contains(other: ValueRange) = false
  override def contains(v: Double) = false
}

