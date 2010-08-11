package org.cloudfun.data

/**
 * Simple immutable data that wraps a map.
 */
case class MapData(values: Map[Symbol, Object]) extends Data {
  def get(name: Symbol) = values.get(name)
  def properties = values.keys
  override def toMap = values
  def contains(name: Symbol) = values.contains(name)

  def set(name: Symbol, value: Object) = throw new UnsupportedOperationException("Modification not supported")
}

