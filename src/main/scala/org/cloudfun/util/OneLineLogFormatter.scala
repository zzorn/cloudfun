package org.cloudfun.util

import _root_.java.text.DateFormat
import _root_.java.util.Date
import _root_.java.util.logging.{LogRecord, Formatter}

/**
 * Formats log messages in a sane-to-read way.
 */
object OneLineLogFormatter extends Formatter {
  def format(record: LogRecord): String = {
    val time = new Date(record.getMillis)
    val sb = new StringBuilder()
    sb.append(time.toGMTString)
      .append("  [")
      .append(record.getLevel.toString)
      .append("]  ")
/*
      .append(record.getLoggerName)
      .append(":  ")
*/
      .append(record.getMessage)
      .append("\n")

    if (record.getThrown != null) {
      sb.append("\n  ")
        .append(record.getThrown)
        .append("\n    ")
        .append(record.getThrown.getStackTrace.mkString("\n    "))
    }

    sb.toString
  }
}

