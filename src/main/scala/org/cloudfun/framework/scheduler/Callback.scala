package org.cloudfun.framework.scheduler

import org.cloudfun.framework.data.Data
import java.io.Serializable


/**
 * Serializable object used for callbacks.
 */
// TODO: Uses direct reference to entity.. replace with reference?
case class Callback(entity: Taskable, parameters: Data) extends Task with Serializable {

  def apply() = entity.callback(parameters)

}

