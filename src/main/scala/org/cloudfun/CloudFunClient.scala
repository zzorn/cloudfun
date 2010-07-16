package org.cloudfun

import authentication.DummyTestAuthenticator
import network.client.ClientNetwork
import scheduler.single.SingleThreadedScheduler
import storage.memory.InMemoryStorage
import time.real.RealClock

object CloudFunClientApp extends CloudFunClient()

/**
 * Default client side class for CloudFun.
 * @param runUpdateThread if true, will start a thread that periodically calls update when start is called.  Otherwise the user needs to periodically call update.
 */
class CloudFunClient(runUpdateThread: Boolean = true) extends CloudFun {

  val clock = RealClock
  val storage = new InMemoryStorage()
  val _scheduler = new SingleThreadedScheduler(clock, !runUpdateThread)
  val network = new ClientNetwork()
  val authenticator = new DummyTestAuthenticator()
  def scheduler = _scheduler

  /**
   * Can be called periodically to manually advance the simulation, if runUpdateThread is set to false.
   */
  def update() {
    _scheduler.update
  }

}

