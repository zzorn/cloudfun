package org.cloudfun.framework.network.protocol

/**
 * Something that can be converted to types that the normal serializers can handle.
 *
 * Transferable implementations should have a constructor that takes a single Object parameter,
 * which is used to convert transfered objects back to class instances.
 */
trait Transferable {

  def toTransferObject : Object

}