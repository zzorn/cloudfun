package org.cloudfun.network

import _root_.org.apache.mina.core.service.IoHandlerAdapter
import _root_.org.apache.mina.core.session.{IdleStatus, IoSession}

/**
 * Handles clients connected to the server
 */

class ClientConnectionHandler extends IoHandlerAdapter {
    override def messageSent(session: IoSession, message: Any) = {}

    override def messageReceived(session: IoSession, message: Any) = {}

    override def exceptionCaught(session: IoSession, cause: Throwable) = {
      cause.printStackTrace
    }

    override def sessionIdle(session: IoSession, status: IdleStatus) = {}

    override def sessionClosed(session: IoSession) = {}

    override def sessionOpened(session: IoSession) = {}

    override def sessionCreated(session: IoSession) = {}
  }

