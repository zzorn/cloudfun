package org.cloudfun.data

import org.scalaprops.Property

/**
 * A map that is always empty and cant be changed.
 */
object EmptyData extends Data {

  override def addProperty[T](property: Property[T]): Property[T] = throw new UnsupportedOperationException("Modification not supported")
}
