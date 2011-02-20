package org.cloudfun.hackworld.server.model

import org.cloudfun.framework.data.Data
import org.scalaprops.Bean
import org.cloudfun.framework.storage.{Storable, Ref}
import org.cloudfun.framework.entity.Entity
;

/**
 * A player account.
 */
class Account extends Storable {

  val username = p('username, "")
  val characters = p[List[Ref[Entity]]]('characters, Nil)

}