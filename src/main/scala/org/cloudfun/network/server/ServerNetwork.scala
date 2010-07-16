package org.cloudfun.network

import _root_.org.apache.mina.filter.codec.ProtocolCodecFilter
import _root_.org.apache.mina.filter.logging.LoggingFilter
import _root_.org.apache.mina.transport.socket.nio.NioSocketAcceptor
import protocol.binary.BinaryProtocol

/**
 * Listens to incoming client connections and attaches them to their accounts/avatars, or creates new ones if they are new.
 */
class ServerNetwork(log: Boolean) extends Network {

  private var acceptor = new NioSocketAcceptor()

  if (log) acceptor.getFilterChain().addLast( "logger", new LoggingFilter() )
  acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter(new BinaryProtocol() ))
  acceptor.setHandler( new ClientConnectionHandler() )

  // TODO: Disconnect if client idle for 30 minutes or so
  //  acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 )

  def start() {
    acceptor.bind
  }

  def stop() {
    acceptor.unbind
  }

}

