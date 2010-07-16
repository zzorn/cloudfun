package org.cloudfun.util

import java.util.logging.{Level, Logger}

/**
 * A logging utility that provides the logging methods in LogMethods and logs to a Logger with a specified path.
 */
class LogUtil(path : String) extends LogMethods {

  override lazy val logger : Logger = Logger.getLogger( path )

  override def log(level: Level, message: => String, exception: => Throwable)  {

    if (logger != null && logger.isLoggable(level) )
    {
      logger.log( level, message, exception )
    }
  }

}