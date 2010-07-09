package org.cloudfun.scheduler

import org.cloudfun.time.Time


/**
 * A service for scheduling callbacks.
 */
trait SchedulerService {

  def scheduleCallback(time: Time, callback: Task)

}
