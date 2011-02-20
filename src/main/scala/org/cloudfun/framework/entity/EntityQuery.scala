package org.cloudfun.framework.entity

/**
 * 
 */
trait EntityQuery {

  def describe: String

  def matches(entity: Entity): Boolean

}