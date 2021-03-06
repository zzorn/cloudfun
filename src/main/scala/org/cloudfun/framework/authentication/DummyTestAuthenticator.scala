package org.cloudfun.framework.authentication

import _root_.java.lang.String
import _root_.org.cloudfun.framework.entity.Entity
import _root_.org.cloudfun.framework.game.GameService
import _root_.org.cloudfun.framework.storage.{Ref, Storage}

/**
 * Naive authenticator.  Not for production use.
 * Doesn't save accounts, doesn't deal with multithreading, saves passwords unscrambled in memory.
 */
class DummyTestAuthenticator(storage: Storage, gameService: GameService) extends Authenticator{

  private var accounts: Map[String, (Ref[Entity], Array[Char])] = Map()

  def createAccount(accountName: String, pw: Array[Char]): AccountCreationResponse = {
    if (accountName == null || accountName.length < 3 || accounts.contains(accountName)) AccountNameUnavailable
    else if (tooWeakPassword(pw, accountName)) TooWeakPassword
    else {
      val accountEntity = gameService.createEntityForNewUser(accountName)
      storage.store(accountEntity)
      println("Retrieving stored acc in dummy test auth " + storage.get(accountEntity.ref))
      val accountRef = storage.getReference[Entity](accountEntity)

      accounts = accounts + (accountName -> (accountRef, pw))
      AccountCreated(accountRef)
    }
  }

  def authenticate(accountName: String, pw: Array[Char]): Option[Ref[Entity]] = {
    accounts.get(accountName) match {
      case Some((acc, pass)) => if (pass == pw) Some(acc) else None
      case None => None
    }
  }

  private def tooWeakPassword(pw: Array[Char], accountName: String): Boolean = {
    pw == null || pw.length < 6 || accountName.toArray == pw
  }

}

