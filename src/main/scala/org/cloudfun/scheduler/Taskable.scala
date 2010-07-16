package org.cloudfun.scheduler

import org.cloudfun.time.Time
import org.cloudfun.data.Data
import org.cloudfun.CloudFun

/**
 * 
 */
trait Taskable {

  def scheduleCallback(time: Time, parameters: Data) = schedulerService.scheduleCallback(time, new Callback(this, parameters))

  private def schedulerService: Scheduler = throw new UnsupportedOperationException("Not implemented yet") // TODO

  /**
   * Method that gets called when a callback event occurs.
   */
  def callback(parameters: Data) {}

}
