package org.cloudfun.network.protocol.binary

import _root_.org.apache.mina.core.buffer.IoBuffer
import _root_.org.cloudfun.data.Data
import _root_.org.cloudfun.network.protocol.DataProtocol


/**
 * A binary message encoding protocol.
 */
// TODO: This will be called to deserialize messages from the client before it has logged in, so should not allow any security breahces (in particular, instantiated class constructors should not alter game state)
// TODO: Create one that packs commonly used Symbols with lookup tables - server tells client about added aliases
// TODO: We could use a cached buffer array in each protocol that is the size of the maximum allowed size of a message?
@serializable
@SerialVersionUID(1)
class BinaryProtocol extends DataProtocol {

  private val serializer : BinarySerializer = new BinarySerializer()

  def decodeData(receivedBytes: IoBuffer): List[Data] = {
    var messages: List[Data] = Nil

    // There may be multiple messages in the buffer, decode until it is empty
    while ( receivedBytes.hasRemaining ) {

      // TODO: Start each message with a byte message type id?  Allows easy adding of protocol related messages such as defining aliases, or introducing new protocol types.  As well as stuff like disconnect, timeout, etc?
      
      val data  = serializer.decode[Data](receivedBytes)
      messages = messages ::: List(data)
    }

    messages
  }

  def encodeData(message: Data) : IoBuffer = {
    val buffer = IoBuffer.allocate(256)
    buffer.setAutoExpand(true)

    serializer.encode(buffer, message)

    buffer.flip()

    buffer
  }

}




