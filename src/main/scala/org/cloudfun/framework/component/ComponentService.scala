package org.cloudfun.framework.component

import org.cloudfun.framework.data.{EmptyData, Data}
import org.cloudfun.framework.storage.Storable
import org.cloudfun.framework.Service

/**
 * Keeps track of the component that can be created when loading data.
 */
trait ComponentService extends Service {

  /**
   * The component types that have been registered.
   */
  def facetTypes: Map[Symbol, ComponentType[_ <: Storable]]

  /**
   * Register a component type, along with a factory method for it.
   */
  def registerFacetType[T <: Storable](facetName: Symbol, factory: () => T, facetKind: Class[T])

  /**
   * Returns true if there is a component type with the specified name.
   */
  def hasFacetType(facetName: Symbol): Boolean

  /**
   * Return the component type with the specified name, if it exists.
   */
  def getFacetType(facetName: Symbol): Option[ComponentType[_ <: Storable]]

  def getComponentType(obj: Storable): Option[ComponentType[_ <: Storable]]

  /**
   * Creates a new component of the specified type and with the specified parameters, if the named component type was found.
   * Does not store the component in the persistence service.
   * Does not call initialize for the component.
   */
  def createComponent(componentType: Symbol, properties: Map[Symbol, AnyRef] = Map(), storedFlag: Boolean = false): Option[_ <: Storable]

}

