package org.cloudfun.storage.memory

import _root_.org.cloudfun.storage.{Storable, Ref}

case class MemoryRef[T <: Storable](obj: T) extends Ref[T] 

