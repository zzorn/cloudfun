package org.cloudfun.framework.storage.mongodb

import _root_.org.bson.types.ObjectId
import _root_.org.cloudfun.framework.storage.{Storable, Ref}

case class MongoRef[T <: Storable](id: ObjectId) extends Ref[T] 
