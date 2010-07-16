package org.cloudfun.network.protocol

import _root_.org.apache.mina.core.buffer.IoBuffer
import _root_.org.apache.mina.core.session.IoSession
import _root_.org.apache.mina.filter.codec._
import _root_.org.cloudfun.data.Data
import _root_.org.skycastle.entity.EntityId
import java.nio.ByteBuffer

/**
 * A way of encoding and decoding Messages to and from a stream of bytes.
 * Used for communication between client and server.
 */
@serializable
@SerialVersionUID(1)
trait DataProtocol extends ProtocolCodecFactory  {

  /**
   * Encodes a Data object to a buffer.
   * Throws an Exception if there was some problem.
   */
  def encodeData(message : Data): IoBuffer

  /**
   * Decodes a message from a buffer to a Data object.
   * Throws an Exception if there was some problem.
   */
  def decodeData(receivedBytes : IoBuffer): List[Data]

  val encoder: ProtocolEncoder = new ProtocolEncoderAdapter {
    def encode(session: IoSession, message: Any, out: ProtocolEncoderOutput) = {
      message match {
        case data: Data => out.write(encodeData(data))
        case _ => throw new Exception("Unknown type for message: " + message)
      }
    }
  }

  val decoder: ProtocolDecoder = new ProtocolDecoderAdapter {
    def decode(session: IoSession, in: IoBuffer, out: ProtocolDecoderOutput) = {
      decodeData(in) foreach out.write(_)
    }
  }

  def getEncoder(session: IoSession): ProtocolEncoder = encoder
  def getDecoder(session: IoSession): ProtocolDecoder = decoder

}


