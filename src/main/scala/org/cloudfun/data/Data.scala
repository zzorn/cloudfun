package org.cloudfun.data

object EmptyData extends Data {
  def get(name: Symbol) = None
  def properties = Nil
  override def toMap = Map()
  def contains(name: Symbol) = false
}

case class MapData(values: Map[Symbol, Object]) extends Data {
  def get(name: Symbol) = values.get(name)
  def properties = values.keys
  override def toMap = values
  def contains(name: Symbol) = values.contains(name)
}

/**
 * Collection of key-value properties.
 */
trait Data {

  /** Get property if found */
  def get(name: Symbol): Option[Object]

  /** True if a property with the specified name is present. */
  def contains(name: Symbol): Boolean

  /** The available properties. */
  def properties: Iterable[Symbol]

  /** The data as a map. */
  def toMap: Map[Symbol, Object] = Map() ++ (properties map (x => x -> apply(x) ))

  /** Get property, or throw error if not present */
  def apply(name: Symbol): Object = if (contains(name)) get(name).get else throw new IllegalArgumentException("Unknown data field '"+name+"'")
  def applyAs[T](name: Symbol): T = apply(name).asInstanceOf[T]

  // Getter utilities
  def get(name: Symbol, default: Object): Object = get(name).getOrElse(default)
  def getAs[T](name: Symbol): Option[T] = if (contains(name) && apply(name).isInstanceOf[T]) Some(applyAs[T](name)) else None
  def getAs[T](name: Symbol, default: T): T = getAs[T](name).getOrElse(default)

  // Getter utilities for specific types
  def getInt(name: Symbol, default: Int = 0) = getAs[Number](name).map(n => n.intValue).getOrElse(default)
  def getLong(name: Symbol, default: Long = 0) = getAs[Number](name).map(n => n.longValue).getOrElse(default)
  def getFloat(name: Symbol, default: Float = 0) = getAs[Number](name).map(n => n.floatValue).getOrElse(default)
  def getDouble(name: Symbol, default: Double = 0) = getAs[Number](name).map(n => n.doubleValue).getOrElse(default)
  def getBoolean(name: Symbol, default: Boolean) = getAs[Boolean](name).getOrElse(default)
  def getString(name: Symbol, default: String) = getAs[String](name).getOrElse(default)
  def getList(name: Symbol, default: List[Object] = Nil) = getAs[List[Object]](name).getOrElse(default)
  def getData(name: Symbol, default: Data = EmptyData) = getAs[Data](name).getOrElse(default)

}


