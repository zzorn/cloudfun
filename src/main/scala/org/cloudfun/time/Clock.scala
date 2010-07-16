package org.cloudfun.time

import scala.math._

/**
 * Provides current game time.
 */
trait Clock {
  
  def currentGameTime: Time

  def millisecondsUntil(time: Time): Long = max(0, time.ms - currentGameTime.ms)
}

