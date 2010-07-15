package org.cloudfun.entity

import org.cloudfun.data.Data
import org.cloudfun.storage.{Ref, Storable}
import org.cloudfun.util.ListenableList
import org.cloudfun.CloudFun

object Entity {

  def create(entityDefinition: Data): Entity = {
    val entity: Entity = new Entity()

    // Create facets
    def createFacet(entry: (Symbol, Data)): Option[Facet] = CloudFun.facetService.createFacet(entry._1, entry._2)
/* TODO
    Entity((entityDefinition.evaluate().asInstanceOf[Data].values map createFacet).flatten)
*/
    null
  }

  def apply(facets: Facet*): Entity = Entity(facets.toList)

  def apply(facets: Iterable[Facet]): Entity = {
    val entity: Entity = new Entity()

    // Set facets
    facets foreach {(f:Facet) => entity.addFacet(f)}

    // Store entity
    CloudFun.storage.store(entity)

    entity
  }

}


/**
 *  A persistent object consisting of different parts (facets).
 */
class Entity extends Storable {

  val facets = list[Ref[Facet]]('facets)
  
  def addFacet(facet: Facet) {
    val r = facet.ref
    if (!facets.contains(r)) {
      facets += r
      facet.entity := this.ref[Entity]
    }
  }

  def removeFacet(facet: Facet) {
    val r = facet.ref
    if (facets.contains(r)) {
      facets -= r
      facet.entity := null
    }
  }

  def facet[T <: Facet](implicit m: Manifest[T]): Option[T] = facets().map(_.get).find(f => m.erasure.isInstance(f)).asInstanceOf[Option[T]]

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
    "Entity " + hashCode + "¸ facets: " + facets().map(_.get()).mkString("[", ", ", "]")
  }

  /**
   * Replaces this entity with the specified new entities, at the same location.
   */
  def replaceWith(entities: Entity *) {
/* TODO: This is special case code...
    facet[Item] match {
      case Some(item) => {
        // Spawn replacements:
        entities foreach {item.space.add(_, item.position)}
      }
      case None => // This entity isn't in a space, so dont put the replacement in a space
    }

    // Remove self
    delete()
*/
  }

}

