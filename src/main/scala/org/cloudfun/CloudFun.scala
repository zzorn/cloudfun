package org.cloudfun

import authentication.Authenticator
import entity.{FacetServiceImpl, FacetService}
import network.Network
import storage.memory.InMemoryStorage
import time.Clock
import time.real.RealClock
import scheduler.single.SingleThreadedScheduler
import scheduler.Scheduler
import storage.Storage

/**
 *
 */
trait CloudFun extends CloudFunService {

  def storage: Storage
  def clock: Clock
  def scheduler: Scheduler
  def authenticator: Authenticator
  def network: Network

  final override def subServices: List[CloudFunService] = List(storage, clock, scheduler, authenticator, network)

}


