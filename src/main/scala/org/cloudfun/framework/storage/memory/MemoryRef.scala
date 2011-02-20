package org.cloudfun.framework.storage.memory

import _root_.org.cloudfun.framework.storage.{Storable, Ref}

case class MemoryRef[T <: Storable](obj: T) extends Ref[T] 

