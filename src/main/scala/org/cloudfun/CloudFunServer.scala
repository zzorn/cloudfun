package org.cloudfun

import authentication.{DummyTestAuthenticator}
import network.ServerNetwork
import scheduler.pooled.PooledScheduler
import storage.mongodb.MongoDbStorage
import time.real.RealClock

/**
 * Singleton instance, allows the server to be started directly.
 */
object CloudFunServerApp extends CloudFunServer


/**
 * CloudFun server, can be invoked programmatically.
 */
// TODO: Use Guice or something for inversion of control service composition?   Is it needed?
class CloudFunServer() extends CloudFun {
  val _clock = RealClock
  val _storage = new MongoDbStorage()
  val _scheduler = new PooledScheduler(_clock, _storage)
  val _authenticator = new DummyTestAuthenticator()
  val _network = new ServerNetwork(_authenticator)

  def network = _network
  def authenticator = _authenticator
  def scheduler = _scheduler
  def clock = _clock
  def storage = _storage
}

