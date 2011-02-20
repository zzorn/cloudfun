package org.cloudfun.framework

import network.Network
import scheduler.Scheduler
import storage.Storage
import time.Clock

/**
 * Singleton used to access the storage and other services.
 */
object CloudFun {

  var application: CloudFunApplication = null

  def clock: Clock = application.clock
  def storage: Storage = application.storage
  def scheduler: Scheduler = application.scheduler
  def network: Network = application.network


}

