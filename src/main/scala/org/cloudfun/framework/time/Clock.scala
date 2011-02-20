package org.cloudfun.framework.time

import scala.math._
import org.cloudfun.framework.Service

/**
 * Provides current game time.
 */
trait Clock extends Service {
  
  def currentGameTime: Time

  def millisecondsUntil(time: Time): Long = max(0, time.ms - currentGameTime.ms)
}

