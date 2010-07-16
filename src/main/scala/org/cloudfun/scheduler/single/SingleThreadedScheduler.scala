package org.cloudfun.scheduler.single

import _root_.org.cloudfun.time.{Clock, Time}
import org.cloudfun.scheduler.{Task, Scheduler}
import java.util.PriorityQueue


/**
 * Simple single-threaded scheduler that can be advanced by calling an update function.
 * Useful for integrating in a render-update loop on a client.
 */
class SingleThreadedScheduler(clock: Clock) extends Scheduler {

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

  def isRunning: Boolean = active

  def start(updatesPerSecond: Int = 60) {
    if (isRunning) stop()

    updater = new Thread(new Runnable {
      def run = {
        while(active) {
          update()
          Thread.sleep(1000 / updatesPerSecond)
        }
      }
    })

    updater.setDaemon(true)

    active = true
    updater.start()
  }

  def stop() {
    if (isRunning) {
      active = false
      updater.join
      updater = null
    }
  }

}
