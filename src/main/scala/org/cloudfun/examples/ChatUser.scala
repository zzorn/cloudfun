package org.cloudfun.examples

import org.cloudfun.entity.Component
import org.cloudfun.data.Data

/**
 * 
 */
class ChatUser extends Component {
  val name = p('name, "Chat User")
  
  def onMessage(message: Data) = null
}