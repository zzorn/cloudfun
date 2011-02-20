package org.cloudfun.framework.storage

import org.cloudfun.framework.Service
import org.cloudfun.framework.data.Data

/**
 * A service for storing objects persistently and retrieving them.
 */
trait Storage extends Service {

  private var serializers: List[Serializer] = Nil

  /**
   * Saves or updates the object if it has changed.
   */
  def store(obj: Storable)

  /**
   * Sets the binding for the specified name to the specified object.
   * Saves or updates the object.
   */
  def bind(name: Symbol, ref: Ref[Storable])

  /**
   * Returns the reference to the specified object.  If it isn't already stored, stores the object.
   */
  def getReference[T <: Storable](obj: T): Ref[T]

  /**
   * Returns the object for the specified reference, or throws an exception if it was not found.
   */
  def get[T <: Storable](ref: Ref[T]): T

  /**
   * Returns the object for the specified name, or throws an exception if it was not found.
   */
  def get[T <: Storable](name: Symbol): T

  /**
   * Deletes the referenced object if it exists.
   */
  def delete[T <: Storable](ref: Ref[T])

  /**
   * Deletes the specified object if it is stored.
   */
  def delete(obj: Storable)

  /**
   * Deletes the specified name binding if found.
   */
  def delete(name: Symbol)

  /**
   * Specify a serializer that can convert some type of objects to a serializable friendly Data format.
   * Serializers registered later will take precedence over serializers registered earlier (so it's possible to override default serializers)
   */
  def registerSerializer(serializer: Serializer) = serializers = serializer :: serializers

  // TODO: Use aliases in network/storage etc layer for actual type?

  /** Convert an object to a storage friendly serialized format */
  def toData(obj: Object): Option[Data] = if (obj==null) None else withSerializer[Data](obj.getClass, _.toData(obj))

  /** Convert an object from a storage friendly serialized format */
  def fromData[T](data: Data, targetType: Class[T]): Option[T] = if (data==null) None else withSerializer[T](targetType, _.fromData(data, targetType))

  private def withSerializer[T](clazz: Class[_], f: Serializer => T): Option[T] = serializers.find(_.canConvert(clazz)).flatMap( x => Some(f(x)) )

}

