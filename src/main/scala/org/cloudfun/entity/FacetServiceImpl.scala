package org.cloudfun.entity

import org.cloudfun.data.Data
import org.cloudfun.CloudFun

class FacetServiceImpl extends FacetService {

  private var _facetTypes: Map[Symbol, Class[_ <: Facet]] = null

  def facetTypes: Map[Symbol, Class[_ <: Facet]] = {
    // Initialize bound facets first time we use them
    if (_facetTypes == null) {
      _facetTypes = Map()
      initFacetTypes()
    }

    _facetTypes
  }

  def getFacetType(facetName: Symbol) = facetTypes.get(facetName)
  def hasFacetType(facetName: Symbol) = facetTypes.contains(facetName)
  def bindFacetType(facetName: Symbol, facetType: Class[_ <: Facet]) = _facetTypes = facetTypes + (facetName -> facetType)

  def createFacet(facetName: Symbol, parameters: Data): Option[_ <: Facet] = {
    if (!hasFacetType(facetName)) {
      println("Warning: Couldn't create facet " + facetName + ": no such facet type exists")
      None
    }
    else if (!parameters.isInstanceOf[Data]) {
      println("Warning: Couldn't create facet " + facetName + ": the parameters are not a Data map")
      None
    }
    else {
      try {
        val facet = facetTypes(facetName).newInstance
        facet.initialize(parameters.asInstanceOf[Data])
        CloudFun.persistenceService.store(facet)
        return Some(facet)
      }
      catch {
        case e: InstantiationException =>
          println("Warning: Couldn't create facet " + facetName + ": " + e.getMessage)
          e.printStackTrace; None // TODO: Log
        case e: IllegalAccessException =>
          println("Warning: Couldn't create facet " + facetName + ": " + e.getMessage)
          e.printStackTrace; None // TODO: Log
      }
    }
  }


  private def initFacetTypes() = {

/* TODO: Better approach?
    bindFacetType('SimpleSpace, classOf[SimpleSpace])
    bindFacetType('Item, classOf[Item])
    bindFacetType('Appearance, classOf[Appearance])
*/

  }
}

