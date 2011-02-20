package org.cloudfun.framework.scheduler.single

import _root_.org.cloudfun.framework.time.{Clock, Time}
import org.cloudfun.framework.scheduler.{Task, Scheduler}
import java.util.PriorityQueue


/**
 * Simple single-threaded scheduler that can be advanced by calling an update function.
 * Useful for integrating in a render-update loop on a client.
 */
class SingleThreadedScheduler(clock: Clock, updateManually: Boolean = false) extends Scheduler {

  val updatesPerSecond = conf[Int]("ups", "updates-per-second", 60, "Number of times per second to check and invoke scheduled tasks.  This value only has effect if updateManually is set to false in the code (it is false by default).")

  private case class ScheduledTask(time: Time, task: Task) extends Comparable[ScheduledTask] {
    def compareTo(o: ScheduledTask) = if (time.ms < o.time.ms) -1 else if (time.ms > o.time.ms) 1 else 0
  }

  private val tasks: PriorityQueue[ScheduledTask] = new PriorityQueue[ScheduledTask]
  private var updater: Thread = null
  private var active = false

  def scheduleCallback(time: Time, task: Task) = tasks add ScheduledTask(time, task)

  def update() {
    val currentTime = clock.currentGameTime

    while(tasks.peek != null && tasks.peek.time.ms <= currentTime.ms) {
      tasks.poll.task()
    }
  }

  def isActive: Boolean = active

  override def onStart() {
    if (!updateManually) {
      if (isActive) onStop()

      updater = new Thread(new Runnable {
        def run = {
          while(active) {
            update()
            Thread.sleep(1000 / updatesPerSecond())
          }
        }
      })

      updater.setDaemon(true)

      active = true
      updater.start()
    }
  }

  override def onStop() {
    if (!updateManually) {
      if (isActive) {
        active = false
        updater.join
        updater = null
      }
    }
  }

}
