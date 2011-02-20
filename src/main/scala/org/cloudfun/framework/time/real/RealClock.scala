package org.cloudfun.framework.time.real

import org.cloudfun.framework.time.{Time, Clock}

/**
 * Time service that returns the real time.
 */
object RealClock extends Clock {
  def currentGameTime = Time(System.currentTimeMillis)
}

