package org.cloudfun.scheduler.local

import _root_.org.cloudfun.time.{Clock, Time}
import org.cloudfun.scheduler.{Task, Scheduler}
import java.util.PriorityQueue


/**
 * Simple scheduler.
 */
class LocalScheduler(clock: Clock) extends Scheduler {

  private case class ScheduledTask(time: Time, task: Task) extends Comparable[ScheduledTask] {
    def compareTo(o: ScheduledTask) = if (time.ms < o.time.ms) -1 else if (time.ms > o.time.ms) 1 else 0
  }

  private val tasks: PriorityQueue[ScheduledTask] = new PriorityQueue[ScheduledTask]

  def scheduleCallback(time: Time, task: Task) = tasks add ScheduledTask(time, task)

  def update() {
    val currentTime = clock.currentGameTime

    while(tasks.peek != null && tasks.peek.time.ms <= currentTime.ms) {
      tasks.poll.task()
    }
  }

  def start() {
    // TODO: Create a worker thread to call update, and change update to invoke the changes in the swing thread??

    // TODO: Is there some existing scheduler library that could be used?
  }


}
