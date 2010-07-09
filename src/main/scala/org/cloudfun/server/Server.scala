package org.cloudfun.server

import java.util.concurrent.Future


/**
 * Facade to a server on client side.
 */
trait Server {

  def name: String
  
  def login(userName: String, password: Array[Char]): Future[Account]
  
}

