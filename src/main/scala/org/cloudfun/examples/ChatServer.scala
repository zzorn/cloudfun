package org.cloudfun.examples

import org.cloudfun.framework.GameServer
import org.cloudfun.framework.storage.memory.InMemoryStorage
import org.cloudfun.framework.storage.Storage

/**
 * 
 */
object ChatServer extends GameServer(new ChatGame()) {
  //override def createStorage = new InMemoryStorage()
  
}
