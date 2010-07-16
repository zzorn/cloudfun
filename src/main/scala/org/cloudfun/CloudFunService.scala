package org.cloudfun

import _root_.java.util.logging.{Logger, FileHandler, ConsoleHandler}
import util.options.OptionParser
import util.{StringUtils, OneLineLogFormatter, LogMethods}

/**
 * Trait for central CloudFun services.
 */
// TODO: Move to utils?
trait CloudFunService extends LogMethods {

  private var running = false

  final var configOptions: List[ConfigOption[_]] = Nil

  /** Adds a config option to the service.  Returns the config option. */
  final def conf[T](abbreviation: String, name: String, default: T, desc: String)(implicit kind: ClassManifest[T]): ConfigOption[T] = {
    val opt = new ConfigOption[T](abbreviation, name, default, desc, kind.erasure.asInstanceOf[Class[T]])
    configOptions = configOptions ::: List(opt)
    opt
  }

  final def allConfigOptions: List[ConfigOption[_]] = configOptions ::: subServices.foldLeft(Nil: List[ConfigOption[_]])(_ ::: _.configOptions)

  /** True if the service has been started. */
  final def isRunning = running

  def serviceName: String = StringUtils.removeTrailing(getClass.getSimpleName, "$") // Remove the trailing $:s that are added by Scala to object instances.

  /** Initialize and configure the service */
  final def init(configValues: Map[String, Object]) {
    if (isRunning) throw new IllegalStateException(serviceName + " is running, can not initialize it")

    logInfo("Initializing " + serviceName)

    subServices foreach (_.init(configValues))

    configOptions foreach (_.readValue(configValues))

    configOptions foreach (o => logInfo("  " + o.toKeyValueString))
    
    onInit()

    logDebug("Initialized "+ serviceName)
  }

  /**
   * Starts the service.
   */
  final def start() {
    if (isRunning) throw new IllegalStateException(serviceName + " is already running, can not start it")

    logInfo("Starting " + serviceName)

    subServices foreach (_.start)

    onStart
    running = true
    logDebug("Started " + serviceName)
  }


  /**
   * Stops the service.
   */
  final def stop() {
    if (!isRunning) throw new IllegalStateException(serviceName + " is already stopped, can not stop it")

    logInfo("Stopping " + serviceName)
    running = false
    onStop

    subServices.reverse foreach (_.stop)

    logDebug("Stopped " + serviceName)
  }

  /** If the service is implemented as an object, automatically provide a main method for starting it and reading command line options. */
  def main(args: Array[String]) {
    // TODO: Add support for reading the configuration from a key-value file, e.g. from the resource path, users home dir, or specified on command line.

    // Setup sane log formatter for all handlers
    val rootLogger = Logger.getLogger("")
    rootLogger.getHandlers foreach (h => h.setFormatter(OneLineLogFormatter))

    logInfo("Running " + serviceName)
    // TODO: Get current version / build from somewhere?

    // Get command line options expected by this service and any subservices and parse inputs
    val parser = new OptionParser(serviceName)
    allConfigOptions foreach (o => parser.add(o.commandLineOption))
    if (parser.parse(args)) {
      init(Map())
      start()
    } else {
      logError("Could not parse command line options, aborted.")
    }
  }

  /** Called when the service is initialized and configuration options have been read. */
  protected def onInit() {}

  /** Called when the service is started. */
  protected def onStart() {}

  /** Called when the service is stopped. */
  protected def onStop() {}

  /** Any services contained in this service, that should be started and stopped when this service is started and stopped. */
  protected def subServices: List[CloudFunService] = Nil

}

