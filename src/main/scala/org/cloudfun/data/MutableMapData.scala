package org.cloudfun.data

import _root_.java.util.HashMap

class MutableMapData extends MutableData {

  private var values: HashMap[Symbol, Object] = new HashMap[Symbol, Object]()

  def contains(name: Symbol) = values.containsKey(name)
  def get(name: Symbol) = if (values.containsKey(name)) Some(values.get(name)) else None
  def properties = values.keySet
  def set(name: Symbol, value: Object) = values.put(name)
  def remove(name: Symbol) = values.remove(name)

}

