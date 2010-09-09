package org.cloudfun.entity

import _root_.org.cloudfun.data.{Data}
import org.cloudfun.messaging.MessageReceiver
import org.cloudfun.storage.{NoRef, Ref, Storable}
import org.cloudfun.util.LogMethods

/**
 *  A persistent object consisting of different parts (component).
 */
final class Entity extends Storable with MessageReceiver with LogMethods {

  def this(component: Iterable[Component]) = {
    this()

    component foreach addComponent
  }

  def this(components: Component*) = this(components.toList)

  val components = p[List[Ref[Component]]]('components, Nil)
  
  final def addComponent(component: Component) {
    val r = component.ref[Component]
    if (!components().contains(r)) {
      components.set(r :: components())
      component.entity := ref[Entity]
    }
  }

  final def removeComponent(facet: Component) {
    val r = facet.ref
    if (components().contains(r)) {
      components.set(components().filterNot(_ == r))
      facet.entity := NoRef[Entity]()
    }
  }

  /**
   * Get a component of the specific type, or None if not found.
   */
//  def component[T <: Component](implicit m: Manifest[T]): Option[T] = component().map(_.apply()).find(f => m.erasure.isInstance(f)).asInstanceOf[Option[T]]

  /**
   * Get a component with the specified name, or None if not found.
   */
  // TODO: Store the component in a map instead, for faster access?
  def component[T <: Component](name: Symbol): Option[T] = components().map(_.apply()).find(f => f.facetType == name).asInstanceOf[Option[T]]

  final def onMessage(message: Data) = {
    message.get[AnyRef]('component) match {
      case None => fallbackMessageHandler(message)
      case Some(name: Symbol) => component[Component](name) match {
        case None => fallbackMessageHandler(message)
        case Some(component: Component) => component.onMessage(message)
      }
    }
  }

  def fallbackMessageHandler(message: Data) {
    logWarning("Message not handled: " + message)
  }

  /**
   * Removes the entity and its component from persistent storage.
   */
  override final def delete() {
    // Delete component
    components().foreach {f: Ref[Component] => f().delete() }

    // Delete self
    super.delete()
  }

  override def toString(): String = {
    "Entity " + hashCode + ", component: " + components().map(_.apply()).mkString("[", ", ", "]")
  }


}

