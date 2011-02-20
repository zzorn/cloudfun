package org.cloudfun.framework.network.server

import _root_.org.apache.mina.core.session.IdleStatus
import _root_.org.apache.mina.filter.codec.ProtocolCodecFilter
import _root_.org.apache.mina.filter.executor.ExecutorFilter
import _root_.org.apache.mina.filter.logging.LoggingFilter
import _root_.org.apache.mina.transport.socket.nio.NioSocketAcceptor
import _root_.org.cloudfun.framework.authentication.Authenticator
import org.cloudfun.framework.storage.Storage
import java.net.InetSocketAddress
import org.cloudfun.framework.network.protocol.binary.BinaryProtocol
import org.cloudfun.framework.network.{Network}

/**
 * Listens to incoming client connections and attaches them to their accounts/avatars, or creates new ones if they are new.
 */
class ServerNetwork(authenticator: Authenticator, storage: Storage) extends Network {

  val DEFAULT_PORT = 6283

  val idleTime = conf[Int]("it", "idle-time", 60*20, "Seconds before an idle client is disconnected.")
  val port = conf[Int]("p", "port", DEFAULT_PORT, "Port to use for the server.")
  val logMessages = conf[Boolean]("lm", "log-messages", false, "Wether to log all network messages.  Only recommended for debugging purposes.")

  private var acceptor: NioSocketAcceptor = null
 
  override protected def onInit() {
    acceptor = new NioSocketAcceptor()

    // TODO: Add possibility of blacklisting certain IP numbers / ranges for counter DDoS purposes - is that effective?

    acceptor.getFilterChain().addLast( "executor", new ExecutorFilter()) // Multithreaded executor pool

    // TODO: Add SSL filter for encryption

    acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter(new BinaryProtocol()))
    if (logMessages()) acceptor.getFilterChain().addLast( "logger", new LoggingFilter() )
    acceptor.getFilterChain().addLast( "authenticator", new AuthenticationFilter(authenticator))

    acceptor.setHandler( new ClientConnectionHandler(storage) )
    acceptor.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, idleTime())
//  acceptor.setReuseAddress(true)  // TODO: Should we use this?  What does it do?

  }

  override protected def onStart() = acceptor.bind(new InetSocketAddress(port()))
  override protected def onStop() = acceptor.unbind(new InetSocketAddress(port()))

}

