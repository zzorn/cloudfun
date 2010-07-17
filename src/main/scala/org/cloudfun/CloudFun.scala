package org.cloudfun

import authentication.Authenticator
import game.GameService
import network.Network
import time.Clock
import scheduler.Scheduler
import storage.Storage

/**
 * A game service, containing the different services making up the game framework.
 */
trait CloudFun extends CloudFunService {

  def storage: Storage
  def clock: Clock
  def scheduler: Scheduler
  def authenticator: Authenticator
  def network: Network
  def gameService: GameService

  final override def subServices: List[CloudFunService] = List(storage, clock, scheduler, authenticator, network, gameService)

}


