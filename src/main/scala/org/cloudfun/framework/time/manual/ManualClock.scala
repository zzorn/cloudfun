package org.cloudfun.framework.time.manual

import _root_.org.cloudfun.framework.time.{Time, Clock}

/**
 * Clock with manually increased time.  For unit testing etc.
 */
object ManualClock extends Clock {

  var time_ms: Long = 0

  def currentGameTime = Time(time_ms)
}

