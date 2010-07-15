package org.cloudfun.entity

/**
 * 
 */
trait EntityQuery {

  def describe: String

  def matches(entity: Entity): Boolean

}