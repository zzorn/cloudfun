package org.cloudfun.storage

import _root_.org.cloudfun.data.Data

/**
 * Something that can convert a type of objects back and forth from Data objects (maps, lists, primitives).
 */
trait Serializer {

  def canConvert(clazz: Class[_]): Boolean

  def fromData[T](data: Data, targetType: Class[T]): T

  def toData(obj: Object): Data
  
}

