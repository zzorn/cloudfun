package org.cloudfun.entity

import _root_.org.cloudfun.messaging.MessageReceiver
import org.cloudfun.scheduler.Taskable
import org.cloudfun.scheduler.Taskable
import org.cloudfun.data.{ Data}
import org.scalaprops.Bean
import org.cloudfun.storage.{NoRef, Ref, Storable}

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

  protected[entity] final def initialize() {init()}

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

