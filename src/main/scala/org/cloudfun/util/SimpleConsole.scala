package org.cloudfun.util

import javax.swing.{JTextField, JTextArea, JFrame}
import javax.swing.text.{SimpleAttributeSet, Document}
import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Dimension, BorderLayout}

/**
 * Simple console with output lines and an input line.
 */
class SimpleConsole(name: String, listener: String => Unit) extends JFrame(name)
{
  private val field: JTextArea = new JTextArea()
  private val input: JTextField = new JTextField()

  field.setEditable(false)

  getContentPane.setLayout(new BorderLayout())
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  getContentPane.add(field, BorderLayout.CENTER)
  getContentPane.add(input, BorderLayout.SOUTH)
  getContentPane.setPreferredSize(new Dimension(800, 600))
  pack()
  setVisible( true )
  input.grabFocus()

  input.addActionListener(new ActionListener() {
    def actionPerformed(e: ActionEvent) = {
      val in = input.getText
      if (in != null && !in.isEmpty) listener(in)
      input.setText("")
    }
  })

  def addLine(message: String) {
    val doc: Document = field.getDocument
    doc.insertString(doc.getLength, message + "\n", SimpleAttributeSet.EMPTY)
  }
}
