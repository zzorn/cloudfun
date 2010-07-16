package org.cloudfun.network

import _root_.java.util.logging.Logger
import _root_.org.apache.mina.core.service.IoHandlerAdapter
import _root_.org.apache.mina.core.session.{IdleStatus, IoSession}
import _root_.org.cloudfun.util.LogMethods

/**
 * Handles clients connected to the server
 */

class ClientConnectionHandler extends IoHandlerAdapter with LogMethods {

  override def sessionOpened(session: IoSession) = {
    // Create session handler for client, or connect the client to some entity directly?
    // We should wait for the session to authenticate first?
    // TODO: Use ssl encrypted session
  }

  override def messageReceived(session: IoSession, message: Any) = {

  }

  override def exceptionCaught(session: IoSession, cause: Throwable) = {
    logError("IO Handler threw an exception when handling communication with session " + session, cause)
  }

  override def sessionIdle(session: IoSession, status: IdleStatus) = {
    // Disconnect the client if it has been idle for the time specified in ServerNetwork (defaults to about 30 minutes)
    if (status == IdleStatus.READER_IDLE || status == IdleStatus.BOTH_IDLE) {
      // TODO: Send notification that the session was closed because idle
      session.close(false)
    }
  }

  override def sessionClosed(session: IoSession) = {
    // TODO: Mark the user as disconnected
  }

}

