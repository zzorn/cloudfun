package org.cloudfun.framework.storage.memory

import _root_.java.util.{HashSet, Collections, HashMap}
import org.cloudfun.framework.storage._

/**
 * Local memory implementation of storage service.
 */
class InMemoryStorage extends Storage {

  private val objects: HashSet[Storable] = new HashSet()
  private val names: HashMap[Symbol, Storable] = new HashMap()

  def delete[T <: Storable](ref: Ref[T]): Unit = delete(getReferencedObject(ref))
  def delete(obj: Storable): Unit = { objects.remove(obj); obj.stored= false }

  def get[T <: Storable](ref: Ref[T]) = getReferencedObject(ref)

  def getReference[T <: Storable](obj: T) = {
    if (!objects.contains(obj)) store(obj)

    MemoryRef[T](obj)
  }

  def store(obj: Storable) = { objects.add(obj); obj.stored = true }

  def storedObjects = Collections.unmodifiableSet(objects)
  def numberOfObjects: Int = objects.size
  
  def delete(name: Symbol) = names.remove(name)

  def get[T <: Storable](name: Symbol) =
    if(names.containsKey(name)) names.get(name).asInstanceOf[T]
    else throw new ElementNotFoundException(name + "' not found")

  def bind(name: Symbol, ref: Ref[Storable]) {
    names.put(name, get(ref))
  }

  private def getReferencedObject[T <: Storable](ref: Ref[T]): T = {
    ref match  {
      case NoRef() => throw new ElementNotFoundException("Reference to nothing")
      case MemoryRef(obj) => obj
      case _ => throw new IllegalStateException("Unknown reference type " + ref)
    }
  }

}

