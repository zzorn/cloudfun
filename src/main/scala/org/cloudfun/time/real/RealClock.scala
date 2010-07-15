package org.cloudfun.time.real

import org.cloudfun.time.{Time, Clock}

/**
 * Time service that returns the real time.
 */
object RealClock extends Clock {
  def currentGameTime = Time(System.currentTimeMillis)
}

