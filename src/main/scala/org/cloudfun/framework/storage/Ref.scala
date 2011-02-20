package org.cloudfun.framework.storage

import _root_.java.io.Serializable
import org.cloudfun.framework.{CloudFun, CloudFunApplication}

/**
 * A reference to a storable object.
 */
trait Ref[T <: Storable] extends Serializable {

  // TODO: We really need the singleton for accessing the services..  Or alternatively reference it in all storables and refs..

  /**
   * Returns the referenced object.
   * Takes the storage to use as an implicit parameter.
   */
  final def apply(): T = CloudFun.storage.get(this)

}

