package org.cloudfun.entity

import org.cloudfun.storage.{Storable}

class ComponentServiceImpl() extends ComponentService {

  private var _componentTypes: Map[Symbol, ComponentType[_ <: Storable]] = Map()

  // Register the entity type so we can serialize and deserialize it
  registerFacetType('entity, () => new Entity(), classOf[Entity])

  def registerFacetType[T <: Storable](facetName: Symbol, factory: () => T, facetKind: Class[T]) = _componentTypes = _componentTypes + (facetName -> ComponentType(facetName, facetKind, factory))
  def facetTypes: Map[Symbol, ComponentType[_ <: Storable]] = _componentTypes
  def hasFacetType(facetName: Symbol) = facetTypes.contains(facetName)
  def getFacetType(facetName: Symbol) = facetTypes.get(facetName)
  def getComponentType(obj: Storable) = _componentTypes.values.find(_.kind.isInstance(obj))

  def createComponent(componentType: Symbol, properties: _root_.scala.Predef.Map[Symbol, AnyRef], storedFlag: Boolean = true): Option[_ <: Storable] = {
    _componentTypes.get(componentType) match {
      case None => None
      case Some(componentType: ComponentType[_]) =>
        
        // Create
        val component: Storable = componentType.create()
        
        component.stored = storedFlag

        // Copy props
        component.addFromMap(properties)

        // Return
        Some(component)
    }
  }

}

