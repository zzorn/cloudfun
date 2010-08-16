package org.cloudfun.entity

import _root_.org.cloudfun.data.{MutableData, Data}
import org.cloudfun.util.ListenableList
import org.cloudfun.CloudFun
import org.cloudfun.messaging.MessageReceiver
import org.cloudfun.storage.{Storage, NoRef, Ref, Storable}

object Entity {

  def create(entityDefinition: Data): Entity = {
    val entity: Entity = new Entity()

    // Create facets
    def createFacet(entry: (Symbol, Data)): Option[Facet] = //CloudFun.facetService.createFacet(entry._1, entry._2)
      throw new UnsupportedOperationException("Not implemented yet") // TODO
/* TODO
    Entity((entityDefinition.evaluate().asInstanceOf[Data].values map createFacet).flatten)
*/
    null
  }

  def apply(facets: Facet*): Entity = Entity(facets.toList)

  def apply(facets: Iterable[Facet]): Entity = {
    val entity: Entity = new Entity()

    throw new UnsupportedOperationException("Not implemented yet") // TODO
/*
    // Set facets
    facets foreach {(f:Facet) => entity.addFacet(f)}
*/

    // Store entity
    throw new UnsupportedOperationException("Not implemented yet") // TODO
    //CloudFun.storage.store(entity)

    entity
  }

}


/**
 *  A persistent object consisting of different parts (facets).
 */
class Entity extends MutableData with Storable with MessageReceiver {

  val facets = list[Ref[Facet]]('facets)
  
  def addFacet(facet: Facet) {
    val r = facet.ref
    if (!facets().contains(r)) {
      facets.set(r :: facets())
      facet.entity := ref[Entity]
    }
  }

  def removeFacet(facet: Facet) {
    val r = facet.ref
    if (facets().contains(r)) {
      facets.set(facets().filterNot(_ == r))
      facet.entity := NoRef
    }
  }

  /**
   * Get a facet of the specific type, or None if not found.
   */
  def facet[T <: Facet](implicit m: Manifest[T], storage: Storage): Option[T] = facets().map(_.apply(storage)).find(f => m.erasure.isInstance(f)).asInstanceOf[Option[T]]

  def onMessage(message: Data) = {
    // TODO: Send message to correct facet?
  }

  /**
   * Removes the entity and its facets from persistent storage.
   */
  override final def delete() {
    // Delete facets
    facets().foreach {f: Ref[Facet] => f().delete() }

    // Delete self
    super.delete()
  }

  override def toString(): String = {
    "Entity " + hashCode + "¸ facets: " + facets().map(_.apply()).mkString("[", ", ", "]")
  }


}

