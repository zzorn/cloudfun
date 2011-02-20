package org.cloudfun.framework.scheduler.pooled

import _root_.org.cloudfun.framework.scheduler.{Task, Scheduler}
import _root_.org.cloudfun.framework.storage.Storage
import _root_.org.cloudfun.framework.time.{Clock, Time}

/**
 * A scheduler that runs tasks in several threads.
 */
class PooledScheduler(clock: Clock, storage: Storage, threadPool: Object = null) extends Scheduler {
  def scheduleCallback(time: Time, callback: Task) = throw new UnsupportedOperationException("Not yet implemented")
}

