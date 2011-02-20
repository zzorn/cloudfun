package org.cloudfun.examples

import org.cloudfun.framework.network.client.ServerHandler
import java.lang.{Exception, String}
import javax.swing.{JTextArea, JTextField, JLabel, JFrame}
import javax.swing.text.{SimpleAttributeSet, Document}
import java.awt.BorderLayout
import java.awt.event.{ActionEvent, ActionListener, ComponentListener}
import org.cloudfun.util.SimpleConsole
import org.cloudfun.framework.data.{MutableData, Data}
import org.cloudfun.framework.GameClient

/**
 * 
 */
object ChatClient extends GameClient() {

  // Listen to incoming messages / specify class that does it

  // Specify some system to create client side state representations, or let it be done automatically?

  // Repeatedly update state

  // Create some UI to display / manipulate client side state

  var console: SimpleConsole = null

  val server: String = "localhost"
  val port: Int = 6283

  override protected def onInit() {}

  override protected def onStart() {
    console = new SimpleConsole("Chat Client", input => network.sendMessage(new MutableData('message, input)) )

    console.addLine("Connecting to " + server + ":" + port)
    network.connect(server, port)

    val msg = new MutableData()
    msg.addProperty('type, 'createAccount)
    msg.addProperty('account, "foobar")
    msg.addProperty('pw, "moobar")
    network.sendMessage(msg)
  }

  override protected def onStop() = {}

  def onMessage(message: Data) {
    console.addLine(message.toString())
  }

  def onDiconnected(reason: String, cause: Exception) {
    console.addLine("Disconnected: " + reason)
  }

  def onConnected() {
    console.addLine("Connected.")
  }

  def onConnectionFailed(reason: String, cause: Exception) {
    console.addLine("Connection failed: " + reason)
  }
}
