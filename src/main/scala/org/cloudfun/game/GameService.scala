package org.cloudfun.game

import _root_.org.cloudfun.entity.Entity
import _root_.org.cloudfun.scheduler.Scheduler
import _root_.org.cloudfun.storage.Storage
import _root_.org.cloudfun.{CloudFun, CloudFunService}

/**
 * A service that creates the initial state in the game and sets up any recurring simulation or other effects,
 * and that creates new entities for new players.
 */
// TODO: Create subclass that is easy to override? (handles calling setupGame on startup and passing in contexts etc)
// TODO: Maybe separate out an interface that users of the library can implement, that just has the setupgame and create new user entity methods.
trait GameService extends CloudFunService {

  /**
   * Called when a new player creates an account, creates a new entity for the player to act as the server
   * side message handler for the player.
   */
  def createEntityForNewUser(userName: String): Entity

}

