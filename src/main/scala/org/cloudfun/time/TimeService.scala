package org.cloudfun.time

import scala.math._

/**
 * Provides current game time.
 */
trait TimeService {
  def currentGameTime: Time

  def delayTo(time: Time): Long = max(0, time.ms - currentGameTime.ms)
}

