package org.cloudfun.network

import _root_.org.apache.mina.core.session.IdleStatus
import _root_.org.apache.mina.filter.codec.ProtocolCodecFilter
import _root_.org.apache.mina.filter.logging.LoggingFilter
import _root_.org.apache.mina.transport.socket.nio.NioSocketAcceptor
import _root_.org.cloudfun.authentication.Authenticator
import protocol.binary.BinaryProtocol
import server.AuthenticationFilter

/**
 * Listens to incoming client connections and attaches them to their accounts/avatars, or creates new ones if they are new.
 */
class ServerNetwork(authenticator: Authenticator, logMessages: Boolean = false, idleClientDisconnectTime_seconds: Int = 60*30) extends Network {

  private var acceptor = new NioSocketAcceptor()

  acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter(new BinaryProtocol()))
  if (logMessages) acceptor.getFilterChain().addLast( "logger", new LoggingFilter() )
  acceptor.getFilterChain().addLast( "authenticator", new AuthenticationFilter(authenticator))

  acceptor.setHandler( new ClientConnectionHandler() )
  acceptor.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, idleClientDisconnectTime_seconds)
//  acceptor.setReuseAddress(true)

  // TODO: Add SSL filter for encryption

  // TODO: Add possibility of blacklisting certain IP numbers / ranges for counter DDoS purposes - is that effective?

  def start() {
    acceptor.bind
  }

  def stop() {
    acceptor.unbind
  }

}

