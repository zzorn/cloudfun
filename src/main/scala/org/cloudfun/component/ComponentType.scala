package org.cloudfun.entity

import org.cloudfun.storage.Storable

/**
 * Metadata about a type of Component.
 */
case class ComponentType[T <: Storable](name: Symbol, kind: Class[T], create: () => T)
