package org.cloudfun

import entity.{FacetServiceImpl, FacetService}
import storage.memory.InMemoryStorage
import time.Clock
import time.real.RealClock
import scheduler.local.LocalScheduler
import scheduler.Scheduler
import storage.Storage

/**
 * A singleton for accessing various services.
 *
 * Set up with local services by default.
 */
object CloudFun {
  var storage: Storage = new InMemoryStorage()
  var schedulerService: Scheduler = new LocalScheduler()
  var timeService : Clock = RealClock
  var facetService: FacetService = new FacetServiceImpl()
}

