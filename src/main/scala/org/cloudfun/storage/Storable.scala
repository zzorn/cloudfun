package org.cloudfun.storage

import _root_.com.mongodb.{DBObject, BasicDBObject}
import _root_.com.osinka.mongodb.MongoObject
import _root_.java.io.Serializable
import _root_.java.lang.String
import _root_.java.util.{HashMap, Map}
import _root_.org.bson.BSONObject
import _root_.org.cloudfun.data.{MutableMapData, MutableData, Data}
import org.cloudfun.CloudFun


/**
 * Persistent object.
 */
// TODO: Save to and load from JSon style key-value map?
trait Storable extends MutableData {

  def ref[T <: Storable]: Ref[T] = storage.getReference[T](this.asInstanceOf[T])
  def store() = storage.store(this)
  def delete() = storage.delete(this)

  protected def storage: Storage = //CloudFun.storage
    throw new UnsupportedOperationException("Not implemented yet") // TODO

/*
  // TODO: mark non-serializable
  private var modified: Boolean = false

  def markAsModified() = modified = true
  def markAsUnmodified() = modified = false
  def isModified(): Boolean = modified
*/

  

}
