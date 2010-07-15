package org.cloudfun.scheduler

import org.cloudfun.time.Time


/**
 * A service for scheduling callbacks.
 */
trait Scheduler {

  def scheduleCallback(time: Time, callback: Task)

}
