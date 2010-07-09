package org.cloudfun.server

import java.io.File
import org.cloudfun.data.Data
import org.cloudfun.entity.Entity
import org.cloudfun.util.options.OptionParser


/**
 * 
 */
object SkycastleServer {

  def main(args: Array[String]) {
    var dataPath: String = "./config"
    val parser = new OptionParser("SkycastleServer") {
      opt("d", "data", "root directory for data files, defaults to " + dataPath, {v: String => dataPath = v})
    }
    if (parser.parse(args)) {

      // Load configuration data
/* TODO
      val configuration: Data = DataLoader.loadDirectory(new File(dataPath))
*/

/*
      // DEBUG:
      println (configuration.toString)
*/

/* TODO
      val simpleWorldData: Data = configuration('org, 'skycastle, 'games, 'simple_world, 'SimpleWorld).get.asInstanceOf[Data]
      val simpleWorld: Entity = Entity.create(simpleWorldData)
*/

/*
      println(simpleWorld)
*/
    }

  }
}