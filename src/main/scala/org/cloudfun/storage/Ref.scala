package org.cloudfun.storage

import _root_.java.io.Serializable
import _root_.org.cloudfun.CloudFun

/**
 * A reference to a storable object.
 */
trait Ref[T <: Storable] extends Serializable {

  /** Returns the referenced object */
  def apply(): T

}

