package org.cloudfun.storage

import _root_.org.cloudfun.CloudFunService
import _root_.org.cloudfun.data.Data

/**
 * A service for storing objects persistently and retrieving them.
 */
trait Storage extends CloudFunService {

  private var serializers: List[Serializer] = Nil

  /**
   * Saves or updates the object if it has changed.
   */
  def store(obj: Storable)

  /**
   * Returns the reference to the specified object.  If it isn't already stored, stores the object.
   */
  def getReference[T <: Storable](obj: T): Ref[T]

  /**
   * Returns the object for the specified reference, or throws an exception if it was not found.
   */
  def get[T <: Storable](ref: Ref[T]): T

  /**
   * Deletes the referenced object if it exists.
   */
  def delete[T <: Storable](ref: Ref[T])

  /**
   * Deletes the specified object if it is stored.
   */
  def delete(obj: Storable)

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

