package pl.self.offersearch.search.query.model

object QueryParam extends Enumeration {
  type QueryParam = Value
  val QUERY,LOCATION,DISTRICT,PAGE,ORDER,REGION,CATEGORY = Value
}