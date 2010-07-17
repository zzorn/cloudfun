package org.cloudfun.storage.mongodb


import _root_.com.mongodb._
import _root_.com.osinka.mongodb.{Serializer, DBObjectCollection, MongoCollection}
import _root_.org.bson.types.ObjectId
import _root_.org.cloudfun.storage.{Ref, Storable, Storage}

/**
 * 
 */
// TODO: Use different names for the database depending on the game name?  So that it is a bit more easy to run different games without having them mess up each others databases accidentally?
class MongoDbStorage() extends Storage {

  val masterStorage = conf[String]("s",  "storage",            ServerAddress.defaultHost, "Primary MongoDB server address.")
  val masterPort    = conf[Int]   ("sp", "storage-port",       ServerAddress.defaultPort, "Primary MongoDB server port.")
  val slaveStorage  = conf[String]("sl", "slave-storage",      "",                        "Secondary MongoDB server address.")
  val slavePort     = conf[Int]   ("slp","slave-storage-port", ServerAddress.defaultPort, "Secondary MongoDB server port.")
  val databaseName  = conf[String]("db", "database-name",      "cloudfun",                "Name of database to use.")

  class StorableCollection(database: DB) extends MongoCollection[Storable] {
    val underlying = database.getCollection("entities")

    def serializer = new Serializer[Storable] {

      def in(obj: Storable): DBObject = {
        throw new UnsupportedOperationException("Not implemented yet") // TODO
/*
        val dbo = new DBObject()
        obj.keys
*/
      }

      def out(dbo: DBObject): scala.Option[Storable] = {
        throw new UnsupportedOperationException("Not implemented yet") // TODO
      }

    }
  }

  private var mongo: Mongo = null
  private var database: DB = null
  private var entityCollection: StorableCollection = null

  override protected def onInit() {
    val masterAddress = new ServerAddress(masterStorage(), masterPort())
    val slaveAddress= new ServerAddress(slaveStorage(), slavePort())

    mongo = if (slaveStorage() == null || slaveStorage() == "") new Mongo(masterAddress) // Single database
            else new Mongo(masterAddress, slaveAddress) // Run in master and slave mode

    database = mongo.getDB(databaseName())
    entityCollection = new StorableCollection(database)

  /*
    entityCollection.createIndex({
      val obj = new DBObject()
      obj.put("id", 0)
      obj
    })
  */
  }


  def store(obj: Storable) {
    if (obj != null) entityCollection += obj
  }

  def delete(obj: Storable) {
    if (obj != null) entityCollection -= obj
  }

  def delete[T <: Storable](ref: Ref[T]) {
    delete( get[T](ref))
  }

  def get[T <: Storable](ref: Ref[T]): T = {
    entityCollection(ref.asInstanceOf[MongoRef[T]].id).asInstanceOf[T]
  }

  def getReference[T <: Storable](obj: T): Ref[T] = {
    var id: ObjectId = obj.get('_id).asInstanceOf[ObjectId]
    if (id == null) {
      id = new ObjectId()
      obj.set('_id, id)
      store(obj)
    }

    return MongoRef[T](id)
  }

}

