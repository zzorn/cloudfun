package org.cloudfun

import authentication.DummyTestAuthenticator
import game.DefaultGameService
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

  val _clock = RealClock
  val _storage = new InMemoryStorage()
  val _scheduler = new SingleThreadedScheduler(_clock, !runUpdateThread)
  val _network = new ClientNetwork()
  val _gameService = new DefaultGameService(this)
  val _authenticator = new DummyTestAuthenticator(_storage, _gameService)

  def gameService = _gameService
  def scheduler = _scheduler
  def clock = _clock
  def storage = _storage
  def network = _network
  def authenticator  = _authenticator

  /**
   * Can be called periodically to manually advance the simulation, if runUpdateThread is set to false.
   */
  def update() {
    _scheduler.update
  }

}

