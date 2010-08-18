package org.cloudfun.data

import org.cloudfun.storage.{NoRef, Ref}

/**
 * Collection of key-value properties.
 */
trait Data {

  // TODO: Can we refer to containing class somehow?
  protected val self: Data = this

  case class Field[T](name: Symbol) {
    def apply(): T = self.getAs[T](name).get
    def get(): Option[T]   = self.getAs[T](name)
    def set(v: T) = self.set(name, v.asInstanceOf[Object])
    def := (v: T) = set(v)
  }

  protected def field[T](name: Symbol, value: T = null) = makeField[T](name, value)
  protected def bool(name: Symbol, value: Boolean = false) = makeField[Boolean](name, value)
  protected def int(name: Symbol, value: Int = 0) = makeField[Int](name, value)
  protected def long(name: Symbol, value: Long = 0) = makeField[Long](name, value)
  protected def float(name: Symbol, value: Float= 0f) = makeField[Float](name, value)
  protected def double(name: Symbol, value: Double= 0.0) = makeField[Double](name, value)
  protected def string(name: Symbol, value: String = "") = makeField[String](name, value)
  protected def data(name: Symbol, value: Data = null) = makeField[Data](name, value)
  protected def list[E](name: Symbol, value: List[E] = Nil) = makeField[List[E]](name, value)
  protected def ref[E](name: Symbol, value: Ref[E] = NoRef[E]()) = makeField[Ref[E]](name, value)

  private def makeField[T](name: Symbol, value: T): Field[T] = {
    set(name, value.asInstanceOf[Object])
    Field[T](name)
  }

  /** Get property if found */
  def get(name: Symbol): Option[Object]

  /** True if a property with the specified name is present. */
  def contains(name: Symbol): Boolean

  def set(name: Symbol, value: Object)

  def update(name: Symbol, value: Object) = set(name, value)

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
  def getString(name: Symbol, default: String = "") = getAs[String](name).getOrElse(default)
  def getList(name: Symbol, default: List[Object] = Nil) = getAs[List[Object]](name).getOrElse(default)
  def getRef[E](name: Symbol, default: Ref[E] = NoRef[E]()) = getAs[Ref[E]](name).getOrElse(default)
  def getData(name: Symbol, default: Data = EmptyData) = getAs[Data](name).getOrElse(default)


  override def toString: String = {
    val sb = new StringBuilder()
    sb.append("{\n")
    properties foreach (p => {
      sb.append(p)
      sb.append(": ")
      sb.append(apply(p).toString())
      sb.append("\n")
    })
    sb.append("}\n")
    sb.toString
  }
}


