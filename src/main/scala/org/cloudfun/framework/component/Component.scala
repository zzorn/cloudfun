package org.cloudfun.framework.component

import _root_.org.cloudfun.framework.network.MessageReceiver
import org.cloudfun.framework.scheduler.Taskable
import org.cloudfun.framework.scheduler.Taskable
import org.cloudfun.framework.data.{ Data}
import org.scalaprops.Bean
import org.cloudfun.framework.storage.{NoRef, Ref, Storable}
import org.cloudfun.framework.entity.Entity

/**
 * A part of an entity, concentrating on a specific area of functionality.
 */
trait Component extends Data with Storable with Taskable with MessageReceiver {

  /**
   * The entity that this component is a part of.
   */
  val entity = refProperty[Entity]('entity)

  /**
   * Name for this type of component.
   */
  def facetType: Symbol = Symbol(getClass.getSimpleName())

  protected[cloudfun] final def initialize() {init()}

  override final def delete() {
    super.delete()

    onDeleted()
  }

  /**
   * Called when after the Component has been created.
   */
  protected def init() {}

  /**
   * Called when the component was deleted.
   */
  protected def onDeleted() {}

  override def toString: String = {
    getClass.getSimpleName + " component " + hashCode
  }


}

