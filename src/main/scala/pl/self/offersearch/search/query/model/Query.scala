package pl.self.offersearch.search.query.model



case class Query(val endpoint: String,val params: Map[QueryParam.Value,String]) {
  
}