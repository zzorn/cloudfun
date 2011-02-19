package org.cloudfun.examples

import org.cloudfun.data.Data
import org.cloudfun.component.Component

/**
 * 
 */
class ChatUser extends Component {
  val name = p('name, "Chat User")
  
  def onMessage(message: Data) = null
}