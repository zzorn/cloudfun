package org.cloudfun.entity

import _root_.org.cloudfun.data.{MutableData, Data}
import org.cloudfun.messaging.MessageReceiver
import org.cloudfun.storage.{NoRef, Ref, Storable}
import org.cloudfun.util.LogMethods


/**
 *  A persistent object consisting of different parts (facets).
 */
class Entity extends MutableData with Storable with MessageReceiver with LogMethods {

  def this(facets: Facet*) = this(facets.toList)

  def this(facets: Iterable[Facet]) = {
    this()

    facets foreach addFacet
  }

  val facets = list[Ref[Facet]]('facets)
  
  final def addFacet(facet: Facet) {
    val r = facet.ref
    if (!facets().contains(r)) {
      facets.set(r :: facets())
      facet.entity := ref[Entity]
    }
  }

  final def removeFacet(facet: Facet) {
    val r = facet.ref
    if (facets().contains(r)) {
      facets.set(facets().filterNot(_ == r))
      facet.entity := NoRef
    }
  }

  /**
   * Get a facet of the specific type, or None if not found.
   */
  def facet[T <: Facet](implicit m: Manifest[T]): Option[T] = facets().map(_.apply()).find(f => m.erasure.isInstance(f)).asInstanceOf[Option[T]]

  /**
   * Get a facet with the specified name, or None if not found.
   */
  // TODO: Store the facets in a map instead, for faster access?
  def facet[T <: Facet](name: Symbol): Option[T] = facets().map(_.apply()).find(f => f.name == name).asInstanceOf[Option[T]]

  final def onMessage(message: Data) = {
    message.get('facet) match {
      case None => fallbackMessageHandler(message)
      case Some(name) => facet(name) match {
        case None => fallbackMessageHandler(message)
        case Some(facet: Facet) => facet.onMessage(message)
      }
    }
  }

  def fallbackMessageHandler(message: Data) {
    logWarning("Message not handled: " + message)
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

