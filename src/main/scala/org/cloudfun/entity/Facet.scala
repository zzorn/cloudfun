package org.cloudfun.entity

import _root_.org.cloudfun.messaging.MessageReceiver
import org.cloudfun.scheduler.Taskable
import org.cloudfun.data.Data
import org.cloudfun.storage.{Ref, Storable}


/**
 * A part of an entity, concentrating on a specific area of functionality.
 */
trait Facet extends Storable with Taskable with MessageReceiver {

  /**
   * The entity that this facet is a part of.
   */
  val entity = ref[Entity]('entity).addListener(onEntityChanged)

  protected[entity] final def initialize(parameters: Data) {init(parameters)}

  /**
   * Called when after the Facet has been created.
   */
  protected def init(parameters: Data) {}



  override final def delete() {
    super.delete()

    onDeleted()
  }

  /**
   * Called when the facet was deleted.
   */
  protected def onDeleted() {}

  /**
   *  Called when the entity that this facet is in has changed.
   */
  protected def onEntityChanged(oldEntity: Ref[Entity], newEntity: Ref[Entity]) {}

  override def toString: String = {
    getClass.getSimpleName + " facet " + hashCode
  }


}

