package org.cloudfun

import scheduler.single.SingleThreadedScheduler
import storage.memory.InMemoryStorage
import time.real.RealClock

/**
 * Default client side class for CloudFun.
 * @param runUpdateThread if true, will start a thread that periodically calls update when start is called.  Otherwise the user needs to periodically call update.
 */
class CloudFunClient(runUpdateThread: Boolean = true) extends CloudFun {

  val _clock = RealClock
  val _storage = new InMemoryStorage()
  val _scheduler = new SingleThreadedScheduler(_clock)

  /**
   * Can be called periodically to manually advance the simulation.
   */
  def update() {
    scheduler.update
  }

  def start() {
    if (runUpdateThread) scheduler.start()
  }

  def stop() {
    if (runUpdateThread) scheduler.stop()
  }

  def storage = _storage
  def clock = _clock
  def scheduler = _scheduler

}

