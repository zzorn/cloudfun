package org.cloudfun.storage

/**
 * A reference to nothing.
 */
case class NoRef[E <: Storable] extends Ref[E]
