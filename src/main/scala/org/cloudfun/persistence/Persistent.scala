package org.cloudfun.persistence

import _root_.java.io.Serializable
import org.cloudfun.CloudFun

/**
 * Storable object.
 */
trait Persistent extends Serializable {

  def ref[T <: Persistent]: Ref[T] = persistenceServices.createReference(this.asInstanceOf[T])
  def markAsModified() = persistenceServices.markForUpdate(this)
  def delete() = persistenceServices.delete(this)
  def store() = persistenceServices.store(this)

  private def persistenceServices: PersistenceService = CloudFun.persistenceService
  
}

