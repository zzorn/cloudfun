package org.cloudfun.examples

import org.cloudfun.entity.Facet
import org.cloudfun.data.Data

/**
 * 
 */
class ChatUser extends Facet {
  val name = p('name, "Chat User")
  
  def onMessage(message: Data) = null
}