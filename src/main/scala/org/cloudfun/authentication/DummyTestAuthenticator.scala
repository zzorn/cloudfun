package org.cloudfun.authentication

import _root_.java.lang.String

/**
 * Naive authenticator.  Not for production use.
 * Doesn't save accounts, doesn't deal with multithreading, saves passwords unscrambled in memory.
 */
class DummyTestAuthenticator extends Authenticator{

  private var accounts: Map[String, (Account, Array[Char])] = Map()

  def createAccount(accountName: String, pw: Array[Char]): AccountCreationResponse = {
    if (accountName == null || accountName.length < 3 || accounts.contains(accountName)) AccountNameUnavailable
    else if (pw == null || pw.length < 6 || accountName.toArray == pw) TooWeakPassword
    else {
      val account = new Account {}
      accounts = accounts + (accountName -> (account, pw))
      AccountCreated(account)
    }
  }

  def authenticate(accountName: String, pw: Array[Char]): Option[Account] = {
    accounts.get(accountName) match {
      case Some((acc, pass)) => if (pass == pw) Some(acc) else None
      case None => None
    }
  }

}

