package org.cloudfun.game

import _root_.org.cloudfun.entity.Entity
import _root_.org.cloudfun.util.StringUtils
import org.cloudfun.{GameServer, CloudFunApplication}

/**
 * A factory that creates the initial state in the game and sets up any recurring simulation or other effects,
 * and that creates new entities for new players.
 */
trait Game {

  /**
   * A fairly stable and unique string id for the game, for use in database names and similar.
   * Defaults to the name of the game class.
   */
  def identifier: String = StringUtils.removeTrailing(getClass.getSimpleName, "$")

  /**
   * Human readable name of the game, for use in titles, server listings, etc.  Can contain spaces and special characters.
   * Defaults to game identifier.
   */
  def name: String = identifier

  /**
   * An optional description of the game.  For use in metaserver listings or the like.
   */
  def description: String = null

  // TODO: Add some icon or similar?
  
  /**
   * Called when the game is started, if it is the first time the game is started.
   * Should initialize the gameworld and set up any simulations or recurring events.
   */
  def setupGame(context: GameServer)

  /**
   * Called when a new player creates an account.
   * Should create an initial game entity for the player.
   * Typically this is some account entity that allows the player to create and configure an in-game avatar,
   * and do account specific tasks like configuring an email, but it could also directly be an in-game avatar,
   * depending on the game type.
   */
  def createEntityForNewUser(userName: String, context: GameServer): Entity

}
