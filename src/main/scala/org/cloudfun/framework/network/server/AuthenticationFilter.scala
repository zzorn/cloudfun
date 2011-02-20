package org.cloudfun.framework.network.server

import _root_.org.apache.mina.core.filterchain.{IoFilter, IoFilterAdapter}
import _root_.org.apache.mina.core.session.IoSession
import _root_.org.cloudfun.framework.authentication.{AccountCreationError, AccountCreated, Authenticator}
import _root_.org.cloudfun.framework.data.{MutableData, Data}
import _root_.org.cloudfun.framework.entity.Entity
import _root_.org.cloudfun.framework.storage.Ref
import _root_.org.cloudfun.util.LogMethods

/**
 * Filter that requires users to either authenticate or create a new account before it forwards their messages.
 */
class AuthenticationFilter(authenticator: Authenticator) extends IoFilterAdapter with LogMethods {

  private def createResponse(responseType: Symbol, desc: Symbol): Data = {
    val response = new MutableData()
    response.put('type, responseType)
    response.put('desc, desc)
    response
  }

  private def createError(errorType: Symbol): Data = createResponse('error, errorType)

  private val notAuthenticatedError = createError('NotAuthenticated)
  private val missingAccount = createError('MissingAccountName)
  private val missingPassword = createError('MissingPassword)
  private val invalidLogin = createError('InvalidAccountNameOrPassword)
  private val unknownMessage = createError('UnknownMessage)
  private val loginOk = createResponse('loginResponse, 'LoginOk)
  private val createdOk = createResponse('accountCreationResponse, 'AccountCreated)

  override def messageReceived(nextFilter: IoFilter.NextFilter, session: IoSession, message: Any) = {

    def onError(error: Data) = {
      logWarning("Session " + session + ": " + error.get[Symbol]('desc, 'UnknownError).name)
      session.write(error)
    }

    def getAccountAndPw(data: Data): Option[(String, Array[Char])] = {
      val accountName = data.get('account, "")
      val pw = data.get('pw, "").toCharArray
      if (accountName == null || accountName.length == 0) {
        onError(missingAccount)
        None
      }
      else if (pw == null && pw.length == 0) {
        onError(missingPassword)
        None
      }
      else Some((accountName, pw))
    }

    def login(data: Data) {
      // Authenticate
      getAccountAndPw(data) match {
        case Some((acc, pw)) => authenticator.authenticate(acc, pw) match {
          case Some(account) =>
            session.setAttribute("ACCOUNT", account)
            logInfo("User " + acc + " logged in.")
            session.write(loginOk)
          case None => onError(invalidLogin)
        }
        case _ => // Error messages already sent.
      }
    }

    def createAccount(data: Data) {
      // Create a new account
      getAccountAndPw(data) match {
        case Some((acc, pw)) => authenticator.createAccount(acc, pw) match {
          case AccountCreated(account) =>
            session.setAttribute("ACCOUNT", account)
            logInfo("Created a new account for user " + acc)
            session.write(createdOk)
          case AccountCreationError(errorCode) =>
            onError(createResponse('accountCreationResponse, errorCode))
        }
        case _ => // Error messages already sent.
      }
    }

    message match {
      case data: Data =>
        println("AuthFilter debug, type = " + data.get[AnyRef]('type, null))
        
        val account = session.getAttribute("ACCOUNT").asInstanceOf[Ref[Entity]]
        if      (account == null && data.get[AnyRef]('type, null) == 'login) login(data)
        else if (account == null && data.get[AnyRef]('type, null) == 'createAccount) createAccount(data)
        else if (account == null) onError(notAuthenticatedError) // Not logged in and didn't get login message
        else nextFilter.messageReceived(session, message) // Logged in already, forward message
      case _ => onError(unknownMessage) // Message wasn't a Data message
    }
  }
}
