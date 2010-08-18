package org.cloudfun.entity

import _root_.org.cloudfun.messaging.MessageReceiver
import org.cloudfun.scheduler.Taskable
import org.cloudfun.storage.{Storable}
import org.cloudfun.scheduler.Taskable
import org.cloudfun.data.{MutableData, Data}

/**
 * A part of an entity, concentrating on a specific area of functionality.
 */
trait Facet extends MutableData with Storable with Taskable with MessageReceiver {

  /**
   * The entity that this facet is a part of.
   */
  val entity = ref[Entity]('entity)

  /**
   * Name for this type of facet.
   */
  def name: Symbol = Symbol(getClass.getSimpleName())

  protected[entity] final def initialize(parameters: Data) {init(parameters)}

  override final def delete() {
    super.delete()

    onDeleted()
  }

  /**
   * Called when after the Facet has been created.
   */
  protected def init(parameters: Data) {}

  /**
   * Called when the facet was deleted.
   */
  protected def onDeleted() {}

  override def toString: String = {
    getClass.getSimpleName + " facet " + hashCode
  }


}

