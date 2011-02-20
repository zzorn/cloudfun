package org.cloudfun.framework.component

import org.cloudfun.framework.storage.Storable

/**
 * Metadata about a type of Component.
 */
case class ComponentType[T <: Storable](name: Symbol, kind: Class[T], create: () => T)
