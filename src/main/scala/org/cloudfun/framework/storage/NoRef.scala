package org.cloudfun.framework.storage

/**
 * A reference to nothing.
 */
case class NoRef[E <: Storable] extends Ref[E]
