package org.cloudfun.scheduler

import org.cloudfun.data.Data
import java.io.Serializable


/**
 * Serializable object used for callbacks.
 */
class Callback(entity: Taskable, parameters: Data) extends Task with Serializable {

  def run = entity.callback(parameters)

}

