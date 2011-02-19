package org.cloudfun

import _root_.java.lang.String
import authentication.{Authenticator, DummyTestAuthenticator}
import component.{ComponentServiceImpl, ComponentService}
import entity.{Entity}
import game.{GameService, Game, DefaultGameService}
import network.{Network, ServerNetwork}
import scheduler.pooled.PooledScheduler
import scheduler.Scheduler
import storage.memory.InMemoryStorage
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
class GameServer(game: Game = null) extends CloudFunApplication {

  def createClock: Clock = RealClock
  def createComponentService: ComponentService = new ComponentServiceImpl()
  def createStorage(componentService: ComponentService): Storage = new MongoDbStorage(componentService)
  def createScheduler(clock: Clock, storage: Storage): Scheduler = new PooledScheduler(clock, storage)
  def createGameService(gameServer: GameServer, g: Game): GameService = new DefaultGameService(gameServer, g)
  def createAuthenticator(storage: Storage, gameService: GameService): Authenticator = new DummyTestAuthenticator(storage, gameService)
  def createServerNetwork(authenticator: Authenticator, storage: Storage): ServerNetwork = new ServerNetwork(authenticator, storage)

  final val clock: Clock = service(createClock)
  final val componentService: ComponentService = service(createComponentService)
  final val storage: Storage = service(createStorage(componentService))
  final val scheduler: Scheduler = service(createScheduler(clock, storage))
  final val gameService: GameService = service(createGameService(this, game))
  final val authenticator: Authenticator = service(createAuthenticator(storage, gameService))
  final val network: Network = service(createServerNetwork(authenticator, storage))

}

