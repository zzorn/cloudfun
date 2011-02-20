package org.cloudfun.examples

import org.cloudfun.framework.data.Data
import org.cloudfun.framework.component.Component

/**
 * 
 */
class ChatUser extends Component {
  val name = p('name, "Chat User")
  
  def onMessage(message: Data) = null
}