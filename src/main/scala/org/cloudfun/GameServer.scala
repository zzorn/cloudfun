package org.cloudfun

import _root_.java.lang.String
import authentication.{Authenticator, DummyTestAuthenticator}
import entity.Entity
import game.{GameService, Game, DefaultGameService}
import network.{Network, ServerNetwork}
import scheduler.pooled.PooledScheduler
import scheduler.Scheduler
import storage.mongodb.MongoDbStorage
import storage.Storage
import time.Clock
import time.real.RealClock

/**
 * Singleton instance, allows the server to be started directly.
 */
object GameServerApp extends GameServer()


// TODO: Something that sets up or loads the world, similar to how the new accounts are created.  Should it be a service or a parameter, or maybe just loaded from a disk / db snapshot?
// If it is a service it is at least possible to easily configure with a configuration option

/**
 * CloudFun server, can be invoked programmatically.
 */
// TODO: Use Guice or something for inversion of control service composition?   Is it needed?
class GameServer(game: Game = null) extends CloudFun {
  val clock: Clock = service(RealClock)
  val storage: Storage = service(new MongoDbStorage())
  val scheduler: Scheduler = service(new PooledScheduler(clock, storage))
  val network: Network = service(new ServerNetwork(authenticator, storage))
  val gameService: GameService = service(new DefaultGameService(this, game))
  val authenticator: Authenticator = service(new DummyTestAuthenticator(storage, gameService))

}

