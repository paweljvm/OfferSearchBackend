package pl.self.offersearch.search.service

import pl.self.offersearch.search.query.model.SimpleQuery
import pl.self.offersearch.search.query.model.Query
import pl.self.offersearch.search.query.model.QueryParam

trait QueryConverter {
  def apply(params: Map[QueryParam.Value,String]) : SimpleQuery
}