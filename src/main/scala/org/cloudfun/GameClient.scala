package org.cloudfun

import authentication.DummyTestAuthenticator
import game.{Game, DefaultGameService}
import network.client.ClientNetwork
import network.Network
import scheduler.Scheduler
import storage.Storage
import scheduler.single.SingleThreadedScheduler
import storage.memory.InMemoryStorage
import time.Clock
import time.real.RealClock

object GameClientApp extends GameClient()

/**
 * Default client side class for CloudFun.
 * @param runUpdateThread if true, will start a thread that periodically calls update when start is called.  Otherwise the user needs to periodically call update.
 */
class GameClient(runUpdateThread: Boolean = true) extends CloudFun {

  val clock: Clock = service(RealClock)
  val storage: Storage  = service(new InMemoryStorage())
  val scheduler: SingleThreadedScheduler = service(new SingleThreadedScheduler(clock, !runUpdateThread))
  val network: Network = service(new ClientNetwork())
//  val gameService = service(new DefaultGameService(this, game))
//  val authenticator = service(new DummyTestAuthenticator(storage, gameService))


  /**
   * Can be called periodically to manually advance the simulation, if runUpdateThread is set to false.
   */
  def update() {
    scheduler.update
  }

}

