package org.cloudfun

import _root_.com.mongodb.ServerAddress
import _root_.java.io.File
import scheduler.pooled.PooledScheduler
import storage.mongodb.MongoDbStorage
import time.real.RealClock

/**
 * Encapsulates the configuration options for the CloudFunServer.
 */
// TODO: Add command line arguments parser for server options, and key-value config file reader for them
case class CloudFunServerConfig(storageServer1: ServerAddress = new ServerAddress(),
                                storageServer2: ServerAddress = null,
                                databaseName: String = "cloudfun")

object CloudFunServerConfig {
  def fromArgs(args: Array[String]): CloudFunServerConfig = {
    // TODO: Parse config from args
    CloudFunServerConfig()
  }

  def fromFile(configFile: File): CloudFunServerConfig = {
    // TODO: Parse config
    CloudFunServerConfig()
  }

  def fromKeyValues(configParams: Map[String, String]): CloudFunServerConfig = {
    // TODO: Parse config
    CloudFunServerConfig()
  }
}

object CloudFunServer {
  /**
   * Default main method that runs a CloudFun server.
   */
  def main(args: Array[String]) {
    val config = CloudFunServerConfig.fromArgs(args)
    val server = new CloudFunServer(config)
    server.start
  }
}

/**
 * 
 */
class CloudFunServer(config: CloudFunServerConfig) extends CloudFun {

  val _clock = RealClock
  val _storage = new MongoDbStorage(config.storageServer1, config.storageServer2, config.databaseName)
  val _scheduler = new PooledScheduler(_clock, _storage)
  
  def start() {
  }

  def stop() {
  }

  def storage = _storage
  def clock = _clock
  def scheduler = _scheduler

}

