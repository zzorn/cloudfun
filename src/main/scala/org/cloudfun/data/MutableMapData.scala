package org.cloudfun.data

import scala.collection.JavaConversions._
import _root_.java.util.HashMap

class MutableMapData extends MutableData {

  def this(map :Map[Symbol, Object]) {
    this()
    map foreach (e => set(e._1, e._2))
  }  

  private var values: HashMap[Symbol, Object] = new HashMap[Symbol, Object]()

  def contains(name: Symbol) = values.containsKey(name)
  def get(name: Symbol) = if (values.containsKey(name)) Some(values.get(name)) else None
  def properties = values.keySet
  def set(name: Symbol, value: Object) = values.put(name, value)
  def remove(name: Symbol) = values.remove(name)

}

