package org.cloudfun.examples

import _root_.java.lang.String
import _root_.org.cloudfun.CloudFun
import _root_.org.cloudfun.entity.Entity
import _root_.org.cloudfun.game.Game

/**
 * Example simple chat game.
 */
// TODO: Implement.
// TODO: We need named entities for this.. otherwise there is no way to get hold of the world in later runs.
class ChatGame extends Game {

  def setupGame(context: CloudFun) = {
    context.storage.store(new Entity())
  }

  def createEntityForNewUser(userName: String, context: CloudFun) = new Entity()

}

