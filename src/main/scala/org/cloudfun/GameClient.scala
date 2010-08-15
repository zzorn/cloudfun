package org.cloudfun

import authentication.DummyTestAuthenticator
import game.{Game, DefaultGameService}
import network.client.{ServerHandler, ClientNetwork}
import network.Network
import scheduler.Scheduler
import storage.Storage
import scheduler.single.SingleThreadedScheduler
import storage.memory.InMemoryStorage
import time.Clock
import time.real.RealClock

/**
 * Default client side class for CloudFun.
 * @param runUpdateThread if true, will start a thread that periodically calls update when start is called.  Otherwise the user needs to periodically call update.
 */
abstract class GameClient(runUpdateThread: Boolean = true) extends CloudFun with ServerHandler {

  val clock: Clock = service(RealClock)
  val storage: Storage  = service(new InMemoryStorage())
  val scheduler: SingleThreadedScheduler = service(new SingleThreadedScheduler(clock, !runUpdateThread))
  val network: ClientNetwork = service(new ClientNetwork(this))


  /**
   * Can be called periodically to manually advance the simulation, if runUpdateThread is set to false.
   */
  def update() {
    scheduler.update
  }

}

