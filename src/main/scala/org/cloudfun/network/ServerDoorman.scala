package org.cloudfun.network

import _root_.java.lang.Throwable
import _root_.java.nio.charset.Charset
import _root_.org.apache.mina.core.service.{IoHandlerAdapter, IoHandler, IoAcceptor}
import _root_.org.apache.mina.core.session.{IdleStatus, IoSession}
import _root_.org.apache.mina.filter.codec.textline.TextLineCodecFactory
import _root_.org.apache.mina.filter.codec.ProtocolCodecFilter
import _root_.org.apache.mina.filter.logging.LoggingFilter
import _root_.org.apache.mina.transport.socket.nio.NioSocketAcceptor

/**
 * Listens to incoming client connections and attaches them to their accounts/avatars, or creates new ones if they are new.
 */
// TODO: Just move this directly to the server?
class ServerDoorman {
  private var acceptor = new NioSocketAcceptor()
  acceptor.getFilterChain().addLast( "logger", new LoggingFilter() )
  acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))))
  acceptor.setHandler( new ClientConnectionHandler() )
  acceptor.getSessionConfig().setReadBufferSize( 2048 )
  acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 )

}

