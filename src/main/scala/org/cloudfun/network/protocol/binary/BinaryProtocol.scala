package org.cloudfun.network.protocol.binary

import _root_.org.apache.mina.core.buffer.IoBuffer
import _root_.org.cloudfun.data.Data
import _root_.org.cloudfun.network.protocol.DataProtocol
import _root_.org.skycastle.entity.EntityId
import _root_.org.skycastle.util.Parameters
import java.nio.ByteBuffer


/**
 * A binary message encoding protocol.
 */
// TODO: Create one that packs commonly used Symbols with lookup tables
// TODO: We could use a cached buffer array in each protocol that is the size of the maximum allowed size of a message?
// TODO: Maybe we should allocate some space for the buffer dynamically instead of first calculating the length?
@serializable
@SerialVersionUID(1)
class BinaryProtocol extends DataProtocol {

  val identifier = 'BinaryProtocol

  private val serializer : BinarySerializer = new BinarySerializer()

  def decode(receivedBytes: IoBuffer): List[Data] = {
    var messages: List[Data] = Nil

    // There may be multiple messages in the buffer, decode until it is empty
    while ( receivedBytes.hasRemaining ) {
      val data  = serializer.decode[Data](receivedBytes)
      messages = messages ::: List(data)
    }

    messages
  }

  def encode(message: Data) : IoBuffer = {
    val buffer = IoBuffer.allocate(256)
    buffer.setAutoExpand(true)

    serializer.encode(buffer, message)

    buffer.flip()

    buffer
  }

}




