package org.cloudfun.data

import scala.collection.JavaConversions._

class MutableData extends Data {

  def this(map :Map[Symbol, Object]) {
    this()
    map foreach (e => addProperty(e._1, e._2))
  }

  def this(name: Symbol, value: AnyRef) {
    this()
    addProperty[AnyRef](name, value)
  }



}

