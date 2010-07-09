package org.cloudfun

import entity.{FacetServiceImpl, FacetService}
import persistence.memory.InMemoryPersistenceService
import time.{RealTimeService, TimeService}
import scheduler.local.LocalScheduler
import scheduler.SchedulerService
import persistence.PersistenceService

/**
 * A singleton for accessing various services.
 *
 * Set up with local services by default.
 */
object CloudFun {
  var persistenceService: PersistenceService = new InMemoryPersistenceService()
  var schedulerService: SchedulerService = new LocalScheduler()
  var timeService : TimeService = RealTimeService
  var facetService: FacetService = new FacetServiceImpl()
}

