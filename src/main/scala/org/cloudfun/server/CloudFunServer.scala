package org.cloudfun.server

import _root_.org.cloudfun.CloudFun
import _root_.org.cloudfun.storage.memory.InMemoryStorage
import java.io.File
import org.cloudfun.data.Data
import org.cloudfun.entity.Entity
import org.cloudfun.util.options.OptionParser

/**
 * 
 */
object CloudFunServer {

  def main(args: Array[String]) {
    var dataPath: String = "./data"
    var memory = false
    var port: String = "7766"
    val parser = new OptionParser("CloudFunServer") {
      opt("d", "data", "root directory for database, defaults to " + dataPath, {v: String => dataPath = v})
      booleanOpt("m", "memory", "keeps all data in memory, and does not save it to disk, defaults to " + memory, {v: Boolean => memory = v})
      intOpt("p", "port", "port to listen to incoming client connections on, defaults to " + port, {v: Int => port = v})
    }
    if (parser.parse(args)) {

      // Initialize database
      println("== Storage setup ==")
      val storage = if (memory) {
        println("Running storage in memory only.  Nothing will be persisted.")
        new InMemoryStorage()
      }
      else throw new UnsupportedOperationException("Not yet implemented")
      CloudFun.storage = storage

      // Initialize gametime
      println("== Gametime setup ==")
      // Load last time from database?

      // Initialize scheduler
      println("== Scheduler setup ==")
      // Load schedule queue from database?

      // Initialize network
      println("== Network setup ==")
//      CloudFun.


    }

  }
}

