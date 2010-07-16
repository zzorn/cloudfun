package org.cloudfun

import entity.{FacetServiceImpl, FacetService}
import storage.memory.InMemoryStorage
import time.Clock
import time.real.RealClock
import scheduler.single.SingleThreadedScheduler
import scheduler.Scheduler
import storage.Storage

/**
 *
 */
trait CloudFun {
  def storage: Storage
  def scheduler: Scheduler
  def clock : Clock

  /**
   * Starts the system.
   */
  def start()

  /**
   * Stops the system.
   */
  def stop()
}


