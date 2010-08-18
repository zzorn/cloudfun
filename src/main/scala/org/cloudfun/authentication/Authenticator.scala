package org.cloudfun.authentication

import _root_.org.cloudfun.Service
import _root_.org.cloudfun.entity.Entity
import _root_.org.cloudfun.storage.Ref

sealed trait AccountCreationResponse
case class AccountCreated(account: Ref[Entity]) extends AccountCreationResponse

object AccountCreationError { def unapply(ace: AccountCreationError) = Some(ace.errorCode) }
abstract class AccountCreationError(val errorCode: Symbol) extends AccountCreationResponse
case object AccountNameUnavailable extends AccountCreationError('AccountNameUnavailable)
case object TooWeakPassword extends AccountCreationError('TooWeakPassword)
case object AccountCreationDenied extends AccountCreationError('AccountCreationDenied)

/**
 * Takes care of authenticating a login and creating new accounts, and returning the reference to the users in-game entity.
 */
trait Authenticator extends Service {

  def authenticate(accountName: String, pw: Array[Char]): Option[Ref[Entity]]
  def createAccount(accountName: String, pw: Array[Char]): AccountCreationResponse

}

