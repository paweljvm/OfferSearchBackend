package pl.self.offersearch.search.service.impl.olx

import pl.self.offersearch.search.service.QueryConverter
import pl.self.offersearch.search.query.model.Query
import pl.self.offersearch.search.query.model.SimpleQuery
import pl.self.offersearch.search.query.model.QueryParam
import org.springframework.stereotype.Service
import pl.self.offersearch.consts.Consts
@Service(value=Consts.SpringBeans.OLX_CONVERTER)
class OlxQueryConverter extends QueryConverter {
  
  val ENDPOINT = "https://www.olx.pl/"
  val WITHOUT_LOCATION ="oferty"
  val paramNameMap = Map(QueryParam.DISTRICT -> "search[district_id]",
      QueryParam.PAGE -> "page",
      QueryParam.ORDER -> "search[order]",
      QueryParam.REGION -> "search[region_id]",
      QueryParam.CATEGORY -> "search[category_id]"
  )
  val valueConverters = Map[QueryParam.Value,(String) => (String)]( QueryParam.ORDER -> {  v => "filter_float_price:"+v } ) 
  
  
  def apply(queryParams: Map[QueryParam.Value,String]): SimpleQuery = {
    var queryString = if (queryParams.contains(QueryParam.LOCATION))
          queryParams.get(QueryParam.LOCATION).map { x=> x.toLowerCase }.get 
          else WITHOUT_LOCATION
    queryString += "/q-"+queryParams.get(QueryParam.QUERY).get
    val query = ENDPOINT+queryString
    val params = queryParams
                .filter { case (p,v) => p != QueryParam.QUERY }
                .map { case (p,v) => (paramNameMap.get(p).get, valueConverters.get(p).map { f => f.apply(v) }.getOrElse(v) ) }
    return new SimpleQuery(query,params)

  }
}