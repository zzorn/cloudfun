package org.cloudfun.framework.network.client

import org.cloudfun.framework.network.MessageReceiver

/**
 * Something that receives messages or other connection info from a server.
 */
trait ServerHandler extends MessageReceiver {

  def onConnectionFailed(reason: String, cause: Exception)

  def onConnected()

  def onDiconnected(reason: String, cause: Exception)
}
