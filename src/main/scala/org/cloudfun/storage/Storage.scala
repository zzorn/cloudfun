package org.cloudfun.storage

/**
 * A service for storing objects persistently and retrieving them.
 */
trait Storage {

  /**
   * Saves or updates the object if it has changed.
   */
  def store(obj: Storable)

  /**
   * Returns the reference to the specified object.  If it isn't already stored, stores the object.
   */
  def getReference[T <: Storable](obj: T): Ref[T]

  /**
   * Returns the object for the specified reference, or throws an exception if it was not found.
   */
  def get[T](ref: Ref[T]): T

  /**
   * Deletes the referenced object if it exists.
   */
  def delete(ref: Ref[_])

  /**
   * Deletes the specified object if it is stored.
   */
  def delete(obj: Storable)

}

