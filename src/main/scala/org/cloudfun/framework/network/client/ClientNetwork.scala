package org.cloudfun.framework.network.client

import _root_.org.cloudfun.framework.network.Network
import org.cloudfun.framework.data.Data
import org.apache.mina.transport.socket.nio.NioSocketConnector
import org.apache.mina.filter.logging.LoggingFilter
import org.cloudfun.framework.network.protocol.binary.BinaryProtocol
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.core.service.{IoHandler}
import java.lang.Throwable
import org.apache.mina.core.session.{IdleStatus, IoSession}
import java.net.InetSocketAddress

/**
 * Client side network logic.
 * Takes a ServerHandler as parameter.  For now a client can only be connected to one server.
 */
// NOTE: If we want to connect to several servers from one client, pass in some ServerHandlerFactory instead.
class ClientNetwork(serverHandler: ServerHandler) extends Network {

  private var connector: NioSocketConnector = null
  private var session: IoSession = null

  val connectionTimeout = conf[Int]("ti", "timeout", 10, "Seconds before aborting a connection attempt when there is no answer.")
  val logMessages = conf[Boolean]("lm", "log-messages", false, "Wether to log all network messages.  Only recommended for debugging purposes.")

  private class SessionHandler(serverHandler: ServerHandler) extends IoHandler {
    def messageSent(session: IoSession, message: Any) = {}
    def messageReceived(session: IoSession, message: Any) = serverHandler.onMessage(message.asInstanceOf[Data])
    def exceptionCaught(session: IoSession, cause: Throwable) = logWarning("Exception when handling network connection", cause)
    def sessionIdle(session: IoSession, status: IdleStatus) = {}
    def sessionClosed(session: IoSession) = serverHandler.onDiconnected("Session closed", null)
    def sessionOpened(session: IoSession) = serverHandler.onConnected()
    def sessionCreated(session: IoSession) = {}
  }

  /**
   * Attempts to connect to the specified server.
   */
  def connect(serverAddress: String,
              serverPort: Int,
              account: String = null,
              password: Array[Char] = null) {
    if (session != null) throw new IllegalStateException("connect has already been called")

    val connectFuture = connector.connect(new InetSocketAddress(serverAddress, serverPort));
    connectFuture.awaitUninterruptibly();
    session = connectFuture.getSession();
  }

  def disconnect() {
    if (session != null) {
      val closeFuture = session.close(false)
      closeFuture.awaitUninterruptibly
      session = null
    }
  }

  def sendMessage(message: Data) {
    session.write(message)
  }

  override protected def onInit() {
    connector = new NioSocketConnector()
    connector.setConnectTimeoutMillis(connectionTimeout() * 1000)
    // TODO: Add SSL filter for encryption
    connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter(new BinaryProtocol()))
    if (logMessages()) connector.getFilterChain().addLast( "logger", new LoggingFilter() )

    connector.setHandler(new SessionHandler(serverHandler));
  }

  override protected def onStop() {
    val closeFuture = session.close(true)
    closeFuture.awaitUninterruptibly
    session = null
  }

  override protected def onStart() {}

}

