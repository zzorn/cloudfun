package org.cloudfun.entity

import org.cloudfun.scheduler.Taskable
import org.cloudfun.data.Data
import org.cloudfun.persistence.{Ref, Persistent}


/**
 * A part of an entity, concentrating on a specific area of functionality.
 */
trait Facet extends Persistent with Taskable {

  private var _entity: Ref[Entity] = null

  /**
   * The entity that this facet is a part of.
   */
  def entity: Ref[Entity] = _entity
  def entity_=(newEntity: Ref[Entity]) = {
    val oldEntity = _entity
    _entity = newEntity
    onEntityChanged(oldEntity, newEntity)
  }

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

