package org.cloudfun.framework.network

import org.cloudfun.framework.data.Data


/**
 * Something that can receive messages (from the client or server)
 */
trait MessageReceiver {

  def onMessage(message: Data)

}

