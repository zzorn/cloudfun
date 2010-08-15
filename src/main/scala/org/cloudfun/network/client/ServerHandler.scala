package org.cloudfun.network.client

import org.cloudfun.messaging.MessageReceiver
import org.cloudfun.data.Data

/**
 * Something that receives messages or other connection info from a server.
 */
trait ServerHandler extends MessageReceiver {

  def onConnectionFailed(reason: String, cause: Exception)

  def onConnected()

  def onDiconnected(reason: String, cause: Exception)
}
