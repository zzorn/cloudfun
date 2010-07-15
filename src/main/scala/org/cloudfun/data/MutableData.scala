package org.cloudfun.data

import _root_.org.cloudfun.storage.Ref

/**
 * 
 */
trait MutableData extends Data {

  def set(name: Symbol, value: Object)

  def update(name: Symbol, value: Object) = set(name, value)


/*
  implicit def self: MutableData = this

  case class field[T](name: Symbol, value: T = null)(implicit mutableData: MutableData) {
    if (value != null) this := value

    type Listener = (T, T) => Unit

    private var listeners: List[Listener] = Nil
    
    def set(value: T){
      val old = if (listeners != Nil) apply(null) else nul
      mutableData.set(name, value)
      listeners foreach( _(old, value))
    }
    def get = mutableData.applyAs[T](name)
    def get(default: T) = mutableData.getAs[T](name, default)

    def := (value: T) = set(value)
    def apply() = get
    def apply(default: T) = get(default)

    def addListener(listener: Listener) = listeners = listener :: listeners
    def removeListener(listener: Listener) = listeners = listeners.filterNot(_ == listener)
  }

  case class int(name: Symbol)(implicit m: MutableData) extends field[Int](name)(m)
  case class long(name: Symbol)(implicit m: MutableData) extends field[Long](name)(m)
  case class float(name: Symbol)(implicit m: MutableData) extends field[Float](name)(m)
  case class double(name: Symbol)(implicit m: MutableData) extends field[Double](name)(m)
  case class string(name: Symbol)(implicit m: MutableData) extends field[String](name)(m)
  case class bool(name: Symbol)(implicit m: MutableData) extends field[Boolean](name)(m)

  case class list[E](name: Symbol)(implicit m: MutableData) extends field[List[E]](name)(m) {
    def += (element: E) = set(element :: get(Nil))
    def -= (element: E) = set(get(Nil).filterNot(_ == element))
    def contains(element: E) = get.contains(element)
  }

  case class data(name: Symbol)(implicit m: MutableData) extends field[Data](name)(m)
  case class ref[R](name: Symbol)(implicit m: MutableData) extends field[Ref[R]](name)(m)

*/

}

