package org.cloudfun.framework.game

import _root_.org.cloudfun.framework.entity.Entity
import _root_.org.cloudfun.framework.{Service}

/**
 * A service that creates the initial state in the game and sets up any recurring simulation or other effects,
 * and that creates new entities for new players.
 */
trait GameService extends Service {

  /**
   * Called when a new player creates an account, creates a new entity for the player to act as the server
   * side message handler for the player.
   */
  def createEntityForNewUser(userName: String): Entity

}

