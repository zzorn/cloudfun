package org.cloudfun.messaging

import org.cloudfun.data.Data


/**
 * Something that can receive messages (from the client or server)
 */
trait MessageReceiver {

  def onMessage(message: Data)

}

