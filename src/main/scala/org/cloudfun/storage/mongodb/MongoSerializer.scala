package org.cloudfun.storage.mongodb

import com.osinka.mongodb.Serializer
import org.cloudfun.storage.Storable
import com.mongodb.{BasicDBList, BasicDBObject, DBObject}
import org.cloudfun.data.{MutableData, Data}
import scala.collection.JavaConversions._
import org.bson.types.ObjectId
import org.scalaprops.Property


/**
 * Serializer for converting Storable objects to MongoDB format and back.
 */
object MongoSerializer extends Serializer[Storable] {
  
  def in(obj: Storable): DBObject = {
    val doc = new BasicDBObject()
    doc.put("data", dataToObj(obj))
    doc.put("type", obj.getClass().getName())
    doc
  }

  def out(dbo: DBObject): scala.Option[Storable] = {
    if (dbo == null) None
    else {
      // Get type
      val kind: String = dbo.get("type").asInstanceOf[String]
      if (kind == null) throw new IllegalStateException("Can not unserialize object, it has no type field.")

      // Get data
      val data: Data = objToData(dbo.get("data").asInstanceOf[DBObject])
      if (data == null) throw new IllegalStateException("Can not unserialize object, it has no data field.")

      // TODO: Check that the kind is among allowed types
      // Create instance
      println("MongoSerializer: Creating " +kind)
      val obj: Storable = Class.forName(kind).asInstanceOf[Storable]

      // Copy data
      println("MongoSerializer: Copying data")
      data.properties.values foreach ((p: Property[_]) => obj.put(p.name, p.value))

      Some(obj)
    }
  }

  private def dataToObj(data: Data): DBObject = {
    val doc = new BasicDBObject()
    data.properties.elements foreach ( (e: (Symbol, Property[_])) => {
      var value = e._2.get
      if (value.isInstanceOf[MongoRef[_]]) value = value.asInstanceOf[MongoRef[_]].id
      if (value.isInstanceOf[Data]) value = dataToObj(value.asInstanceOf[Data])
      if (value.isInstanceOf[List[_]]) value = listToObj(value.asInstanceOf[List[_]])
      if (value.isInstanceOf[Set[_]]) value = setToObj(value.asInstanceOf[Set[_]])

      doc.put(e._1.name, value)
    })
    doc
  }

  private def listToObj(list: List[_]): BasicDBList = {
    val doc = new BasicDBList()
    list foreach (e => doc.add(e.asInstanceOf[Object]))
    doc
  }

  private def setToObj(set: Set[_]): BasicDBList = {
    val doc = new BasicDBList()
    set foreach (e => doc.add(e.asInstanceOf[Object]))
    doc
  }

  private def objToData(obj: DBObject): Data= {
    val data = new MutableData()
    obj.toMap.elements foreach ( (e: (_, _)) => {
      var value = e._2
      if (value.isInstanceOf[BasicDBList]) value = objToList(value.asInstanceOf[BasicDBList])
      if (value.isInstanceOf[DBObject]) value = objToData(value.asInstanceOf[DBObject])
      if (value.isInstanceOf[ObjectId]) value = MongoRef[Storable](value.asInstanceOf[ObjectId])

      data.put(Symbol(e._1.toString), value.asInstanceOf[Object])
    })
    data
  }

  private def objToList(list: BasicDBList): List[_] = {
    list.elements.toList
  }
}
