package org.cloudfun.persistence.memory

import org.cloudfun.persistence.{Ref, Persistent}

case class MemoryRef[T <: Persistent](value: T) extends Ref[T]{
  def getForUpdate(): T = value
  def get(): T = value
}

