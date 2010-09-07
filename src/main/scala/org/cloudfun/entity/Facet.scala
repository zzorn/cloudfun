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
trait Facet extends Data with Storable with Taskable with MessageReceiver {

  /**
   * The entity that this facet is a part of.
   */
  val entity = refProperty[Entity]('entity)

  /**
   * Name for this type of facet.
   */
  def facetType: Symbol = Symbol(getClass.getSimpleName())

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

