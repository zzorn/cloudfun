package org.cloudfun.data

import org.cloudfun.storage.{Storable, NoRef, Ref}

/**
 * Collection of key-value properties.
 */
trait Data {

  private var fields: Map[Symbol, Field[_]]

  class Field[T](name: Symbol, initialValue: T) {
    private var value: T = initialValue

    def apply(): T = value
    def := (v: T)  = value = v

    def get: T  = value
    def set(v: T) = value = v
  }

  protected def field[T](name: Symbol, value: T = null) = addField[T](name, value)
  protected def bool(name: Symbol, value: Boolean = false) = addField[Boolean](name, value)
  protected def int(name: Symbol, value: Int = 0) = addField[Int](name, value)
  protected def long(name: Symbol, value: Long = 0) = addField[Long](name, value)
  protected def float(name: Symbol, value: Float= 0f) = addField[Float](name, value)
  protected def double(name: Symbol, value: Double= 0.0) = addField[Double](name, value)
  protected def string(name: Symbol, value: String = "") = addField[String](name, value)
  protected def data(name: Symbol, value: Data = null) = addField[Data](name, value)
  protected def list[E](name: Symbol, value: List[E] = Nil) = addField[List[E]](name, value)
  protected def ref[E <: Storable](name: Symbol, value: Ref[E] = NoRef[E]()) = addField[Ref[E]](name, value)

  private def addField[T](name: Symbol, value: T): Field[T] = {
    val field = new Field[T](name, value)
    fields = fields + (name -> field)
    field
  }

  /** Get property if found */
  def get(name: Symbol): Option[Object] = fields.get(name) match {case None => None; case Some(field) => field.get}

  /** True if a property with the specified name is present. */
  def contains(name: Symbol): Boolean = fields.contains(name)

  def set(name: Symbol, value: Object) = fields.get(name) match {case None => ; case Some(field) => field.set(value)}

  /** The available properties. */
  def properties: Iterable[Symbol] = fields.keys

  /** The data as a map. */
  def toMap: Map[Symbol, Object] = Map() ++ (properties map (x => x -> apply(x) ))
  def fromMap(values: Map[Symbol, Object]) = values foreach (e => set(e._1, e._2)) 

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
  def getRef[E <: Storable](name: Symbol, default: Ref[E] = NoRef[E]()) = getAs[Ref[E]](name).getOrElse(default)
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


