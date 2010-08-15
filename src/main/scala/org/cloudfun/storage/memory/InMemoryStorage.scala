package org.cloudfun.storage.memory

import _root_.java.util.{HashSet, Collections, HashMap}
import org.cloudfun.storage.{ElementNotFoundException, Ref, Storable, Storage}

/**
 * Local memory implementation of storage service.
 */
class InMemoryStorage extends Storage {

  private val objects: HashSet[Storable] = new HashSet()
  private val names: HashMap[Symbol, Storable] = new HashMap()

  def delete[T <: Storable](ref: Ref[T]) = objects.remove(ref.asInstanceOf[MemoryRef[T]].obj)
  def delete(obj: Storable) = objects.remove(obj)

  def get[T <: Storable](ref: Ref[T]) = ref.asInstanceOf[MemoryRef[T]].obj
  
  def getReference[T <: Storable](obj: T) = {
    if (!objects.contains(obj)) objects.add(obj)

    MemoryRef[T](obj)
  }

  def store(obj: Storable) = objects.add(obj)

  def storedObjects = Collections.unmodifiableSet(objects)
  def numberOfObjects: Int = objects.size


  def delete(name: Symbol) = names.remove(name)

  def get[T <: Storable](name: Symbol) =
    if(names.containsKey(name)) names.get(name).asInstanceOf[T]
    else throw new ElementNotFoundException(name + "' not found")

  def bind(name: Symbol, ref: Ref[Storable]) {
    val obj = get(ref)
    names.put(name, obj)
  }
}

