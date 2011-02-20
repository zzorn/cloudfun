package org.cloudfun.framework.storage.mongodb

import com.osinka.mongodb.Serializer
import org.cloudfun.framework.storage.Storable
import com.mongodb.{BasicDBList, BasicDBObject, DBObject}
import org.cloudfun.framework.data.{MutableData, Data}
import scala.collection.JavaConversions._
import org.bson.types.ObjectId
import org.scalaprops.Property
import org.cloudfun.framework.component.{ComponentService, ComponentType}

/**
 * Serializer for converting Storable objects to MongoDB format and back.
 */
class MongoSerializer(componentService: ComponentService) extends Serializer[Storable] {

  private val CF_TYPE = "_cf_type"

  def in(obj: Storable): DBObject = {
    val doc = dataToObj(obj)

    val componentType: Symbol = componentService.getComponentType(obj) match {
      case None => throw new IllegalStateException("Can not store an object of type " + obj.getClass.getName())
      case Some(ct: ComponentType[_]) => ct.name
    }

    doc.put(CF_TYPE, componentType.name)
    doc
  }

  def out(dbo: DBObject): scala.Option[Storable] = {
    if (dbo == null) None
    else {
      if (!dbo.containsField(CF_TYPE)) throw new IllegalStateException("Can not unserialize object, it has no type field ("+CF_TYPE+").")

      // Get type
      val kind = Symbol(dbo.get(CF_TYPE).toString)

      // Get data
      val properties= objToMap(dbo)

      // Build component
      componentService.createComponent(kind, properties, true)
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

  private def objToMap(obj: DBObject): Map[Symbol, AnyRef] = {
    var map = Map[Symbol, AnyRef]()
    obj.toMap.elements foreach ( (e: (_, _)) => {
      val key: Symbol = Symbol(e._1.toString)
      var value: AnyRef = e._2.asInstanceOf[AnyRef]
      if (value.isInstanceOf[BasicDBList]) value = objToList(value.asInstanceOf[BasicDBList])
      if (value.isInstanceOf[DBObject]) value = objToData(value.asInstanceOf[DBObject])
      if (value.isInstanceOf[ObjectId]) value = MongoRef[Storable](value.asInstanceOf[ObjectId])

      map += (key -> value)
    })
    map
  }

  private def objToList(list: BasicDBList): List[_] = {
    list.elements.toList
  }
}
