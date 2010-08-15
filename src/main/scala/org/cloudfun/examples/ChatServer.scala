package org.cloudfun.examples

import org.cloudfun.GameServer
import org.cloudfun.storage.memory.InMemoryStorage
import org.cloudfun.storage.Storage

/**
 * 
 */
object ChatServer extends GameServer(new ChatGame()) {
  override def createStorage = new InMemoryStorage()
  
}
