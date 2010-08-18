package org.cloudfun.game

import _root_.java.lang.String
import _root_.org.cloudfun.entity.Entity
import org.cloudfun.{GameServer, InitializationError, CloudFunApplication}

/**
 * Default GameService implementation that delegates the actual game initialization and new user initialization
 * to a Game provided as a constructor or command line parameter.
 */
class DefaultGameService(gameServer: GameServer, var game: Game = null) extends GameService {

  val gameClassName = conf[String]("g", "game-class", "", "The full classname of the Game implementation to run on the server.  E.g. org.cloudfun.examples.ChatGame")

  override protected def onInit() {
    def error(msg: String, e: Throwable = null) {
      val m = "Initialization failed, " + msg
      throw InitializationError(m, serviceName, e)
    }

    if (!gameClassName().isEmpty) {
      try {
        game = Class.forName(gameClassName()).newInstance().asInstanceOf[Game]
      }
      catch {
        case e : ClassNotFoundException  => error("could not find the Game class '"+gameClassName()+"' specified in the configuration, check that the correct jars are included and that the class name is correct.")
        case e : InstantiationException => error("could not instantiate the Game class '"+gameClassName()+"' specified in the configuration, check that it is a class with a no-argument constructor.", e)
        case e : IllegalAccessException  => error("could not access the Game class '"+gameClassName()+"' specified in the configuration, check that it is not private or protected.", e)
        case e : ClassCastException => error("the class '"+gameClassName()+"' specified in the configuration doesn't seem to implement the Game trait.", e)
        case e : Throwable => error("could not initialize the Game class '"+gameClassName()+"' specified in the configuration.", e)
      }
    }
    else if (game == null) error("no Game class specified on command line, in configuration, or programmatically.")
  }

  override protected def onStart() {
    // Determine if this is the first time the game is started
    // TODO: Store some start counter or similar in a separate statistics database in the storage?
    val isFirstTime = true

    if (isFirstTime) {
      logInfo("No previous state for the game " + game.name + " found, so setting it up")
      game.setupGame(gameServer)
    }
  }

  def createEntityForNewUser(userName: String): Entity = {
    game.createEntityForNewUser(userName, gameServer)
  }

}

