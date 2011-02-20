package org.cloudfun.framework

/**
 * 
 */
case class InitializationError(message: String, service: String, cause: Throwable) extends Exception(message, cause)

