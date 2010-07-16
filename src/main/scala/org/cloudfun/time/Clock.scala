package org.cloudfun.time

import _root_.org.cloudfun.CloudFunService
import scala.math._

/**
 * Provides current game time.
 */
trait Clock extends CloudFunService {
  
  def currentGameTime: Time

  def millisecondsUntil(time: Time): Long = max(0, time.ms - currentGameTime.ms)
}

