package org.cloudfun.scheduler

import _root_.org.cloudfun.Service
import org.cloudfun.time.Time


/**
 * A service for scheduling callbacks.
 */
// TODO: On startup, instead of using a realtime clock, use a clock that is set to the time when a specific task was scheduled to occur
// - that way the previously saved unexecuted tasks are started more gently - that might require running through a lot of periodically reoccuring
// tasks though, but it will give a more realistic simulation of the off-time.  Maybe it could be a startup parameter.
// Maybe that approach could be used for all tasks?  -- It may be too prone to slowness from task code that doesn't take that case into enough consideration though.
trait Scheduler extends Service {

  def scheduleCallback(time: Time, callback: Task)

}
