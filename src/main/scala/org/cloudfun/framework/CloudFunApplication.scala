package org.cloudfun.framework

import network.Network
import time.Clock
import scheduler.Scheduler
import storage.Storage

/**
 * A game service, containing the different services making up the game framework.
 */
trait CloudFunApplication extends Service {

  // Set the context
  CloudFun.application = this

  def clock: Clock
  def storage: Storage
  def scheduler: Scheduler
  def network: Network

}


