package org.cloudfun.storage.memory

import _root_.java.util.{HashSet, Collections, HashMap}
import _root_.org.cloudfun.storage.{Ref, Storable, Storage}
/**
 * Local memory implementation of storage service.
 */
class InMemoryStorage extends Storage {

  private val objects: HashSet[Storable] = new HashSet()

  def delete[T <: Storable](ref: Ref[T]) = objects.remove(ref())
  def delete(obj: Storable) = objects.remove(obj)

  def get[T <: Storable](ref: Ref[T]) = ref()
  
  def getReference[T <: Storable](obj: T) = {
    if (!objects.contains(obj)) objects.add(obj)

    MemoryRef[T](obj)
  }

  def store(obj: Storable) = objects.add(obj)

  def storedObjects = Collections.unmodifiableSet(objects)
  def numberOfObjects: Int = objects.size
}

