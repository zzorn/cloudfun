package org.cloudfun

import _root_.java.lang.String
import authentication.{DummyTestAuthenticator}
import entity.Entity
import game.DefaultGameService
import network.ServerNetwork
import scheduler.pooled.PooledScheduler
import storage.mongodb.MongoDbStorage
import time.real.RealClock

/**
 * Singleton instance, allows the server to be started directly.
 */
object CloudFunServerApp extends CloudFunServer()


// TODO: Something that sets up or loads the world, similar to how the new accounts are created.  Should it be a service or a parameter, or maybe just loaded from a disk / db snapshot?
// If it is a service it is at least possible to easily configure with a configuration option

/**
 * CloudFun server, can be invoked programmatically.
 */
// TODO: Use Guice or something for inversion of control service composition?   Is it needed?
class CloudFunServer() extends CloudFun {
  val _clock = RealClock
  val _storage = new MongoDbStorage()
  val _scheduler = new PooledScheduler(_clock, _storage)
  val _network = new ServerNetwork(_authenticator)
  val _gameService = new DefaultGameService(this)
  val _authenticator = new DummyTestAuthenticator(_storage, _gameService)

  def network = _network
  def authenticator = _authenticator
  def scheduler = _scheduler
  def clock = _clock
  def storage = _storage
  def gameService = _gameService
}

