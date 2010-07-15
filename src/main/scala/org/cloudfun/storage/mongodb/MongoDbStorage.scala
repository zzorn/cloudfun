package org.cloudfun.storage.mongodb


import _root_.com.mongodb._
import _root_.com.osinka.mongodb.{Serializer, DBObjectCollection, MongoCollection}
import _root_.org.bson.types.ObjectId
import _root_.org.cloudfun.storage.{Ref, Storable, Storage}

/**
 * 
 */
class MongoDbStorage(address: ServerAddress, secondaryAddress: ServerAddress = null, databaseName: String = "cloudfun") extends Storage {

  private val mongo: Mongo = if (secondaryAddress == null) {
    // Single db
    new Mongo(address)
  }
  else {
    // Run in master and slave mode
    new Mongo(address, secondaryAddress)
  }

  private val database: DB = mongo.connect(databaseName)
  private val entityCollection = new MongoCollection[Storable] {
    val underlying = database.getCollection("entities")

    def serializer = new Serializer[Storable] {
      def in(obj: Storable): DBObject = {
        val dbo = new DBObject()
        obj.keys
      }

      def out(dbo: DBObject): scala.Option[Storable] = {

      }

    }
  }

/*
  entityCollection.createIndex({
    val obj = new DBObject()
    obj.put("id", 0)
    obj
  })
*/


  def store(obj: Storable) {
    if (obj != null) entityCollection.insert(obj)
  }

  def delete(obj: Storable) {
    if (obj != null) entityCollection.remove(obj)
  }

  def delete(ref: Ref[_]) {
    delete( get(ref))
  }

  def get[T](ref: Ref[T]): Storable = {
    entityCollection.findOne(new BasicDBObject("_id", ref.asInstanceOf[MongoRef].id))
  }

  def getReference[T <: Storable](obj: T): Ref = {
    var id: ObjectId = obj.get("_id").asInstanceOf[ObjectId]
    if (id == null) {
      id = new ObjectId()
      obj.set("_id", id)
    }

    return MongoRef[T](id)
  }

}

