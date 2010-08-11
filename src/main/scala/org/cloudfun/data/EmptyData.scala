package org.cloudfun.data

/**
 * A map that is always empty and cant be changed.
 */
object EmptyData extends Data {
  def get(name: Symbol) = None
  def properties = Nil
  override def toMap = Map()
  def contains(name: Symbol) = false

  def set(name: Symbol, value: Object) = throw new UnsupportedOperationException("Modification not supported")
}
