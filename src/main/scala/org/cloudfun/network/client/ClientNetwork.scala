package org.cloudfun.network.client

import _root_.org.cloudfun.network.Network

/**
 * Client side network logic.
 */
class ClientNetwork extends Network {
  
  /**
   * Attempts to connect to the specified server.
   */
  def connect(serverAddress: String,
              serverPort: Int,
              serverHandler: ServerHandler,
              account: String = null,
              password: Array[Char] = null) {
    
  }

  override protected def onStop() = null
  override protected def onStart() = null
  override protected def onInit() = null

}

