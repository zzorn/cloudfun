package org.cloudfun.authentication

import _root_.org.cloudfun.CloudFunService

sealed trait AccountCreationResponse
case class AccountCreated(account: Account) extends AccountCreationResponse

object AccountCreationError { def unapply(ace: AccountCreationError) = Some(ace.errorCode) }
abstract class AccountCreationError(val errorCode: Symbol) extends AccountCreationResponse
case object AccountNameUnavailable extends AccountCreationError('AccountNameUnavailable)
case object TooWeakPassword extends AccountCreationError('TooWeakPassword)
case object AccountCreationDenied extends AccountCreationError('AccountCreationDenied)

/**
 * 
 */
trait Authenticator extends CloudFunService {

  def authenticate(accountName: String, pw: Array[Char]): Option[Account]
  def createAccount(accountName: String, pw: Array[Char]): AccountCreationResponse

}

