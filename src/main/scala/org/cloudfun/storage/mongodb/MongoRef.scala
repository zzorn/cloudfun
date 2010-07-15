package org.cloudfun.storage.mongodb

import _root_.org.bson.types.ObjectId
import _root_.org.cloudfun.CloudFun
import _root_.org.cloudfun.storage.{Storable, Ref}

/**
 * 
 */
case class MongoRef[T <: Storable](id: ObjectId) extends Ref[T] {
  override def apply(): T = CloudFun.storage.get(this)
}

