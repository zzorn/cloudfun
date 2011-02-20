package org.cloudfun.framework

import org.cloudfun.util.options._

/**
 * An option that can be used to configure an aspect of a service.
 */
case class ConfigOption[T](abbreviation: String, name: String, default: T, description: String, kind: Class[T]) {

  def readValue(configValues: Map[String, Object]) {
    configValues.get(name) match {
      case Some(o) => if (kind.isInstance(o)) set(o.asInstanceOf[T])
      case None =>
    }
  }

  private var configProvided = false
  private var configValue: T = _

  /** The configuration value, or the default value if no value has been set yet */
  def value: T = if (configProvided) configValue else default

  /** The configuration value, or the default value if no value has been set yet */
  def apply() = value

  def set(value: T) {
    configValue    = value
    configProvided = true
  }

  def := (value : T) = set(value)

  def toKeyValueString: String = name + " = " + value

  def commandLineOption: OptionDefinition = {
    def desc = description + "  Defaults to '" + default + "'."

    kind.getSimpleName.toLowerCase match {
      case "string" => new ArgOptionDefinition(abbreviation, name, "<value>", desc, {v => set(v.asInstanceOf[T])})
      case "int" => new IntArgOptionDefinition(abbreviation, name, "<value>", desc, {v => set(v.asInstanceOf[T])})
      case "long" => new IntArgOptionDefinition(abbreviation, name, "<value>", desc, {v => set(v.longValue.asInstanceOf[T])})
      case "double" => new DoubleArgOptionDefinition(abbreviation, name, "<value>", desc, {v => set(v.asInstanceOf[T])})
      case "float" => new DoubleArgOptionDefinition(abbreviation, name, "<value>", desc, {v => set(v.floatValue.asInstanceOf[T])})
      case "boolean" => new BooleanArgOptionDefinition(abbreviation, name, "<value>", desc, {v => set(v.asInstanceOf[T])})
      case _ => throw new IllegalStateException("Unsupported configuration option type: " + kind)
    }

  }
}

