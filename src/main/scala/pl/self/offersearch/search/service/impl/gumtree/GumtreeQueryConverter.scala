package pl.self.offersearch.search.service.impl.gumtree

import pl.self.offersearch.search.service.QueryConverter
import pl.self.offersearch.search.query.model.SimpleQuery
import pl.self.offersearch.search.query.model.QueryParam
import org.springframework.stereotype.Service
import pl.self.offersearch.consts.Consts

@Service(value=Consts.SpringBeans.GUMTREE_CONVERTER)
class GumtreeQueryConverter extends QueryConverter {
 
  val ENDPOINT ="https://www.gumtree.pl/"
 
  
  
  def apply(params: Map[QueryParam.Value, String]): SimpleQuery = {
    val queryString = getQueryString(params)
    val query = ENDPOINT + queryString + 
    params.getOrElse(QueryParam.QUERY,"") +"/" +getSuffix(params)
    new SimpleQuery(query,Map.empty)
  }
  
  def getQueryString(params: Map[QueryParam.Value, String]) : String = {
     if(params.contains(QueryParam.LOCATION) && params.contains(QueryParam.CATEGORY))
       "s-"+params.getOrElse(QueryParam.CATEGORY,"")+"/"+params.getOrElse(QueryParam.LOCATION,"") +"/"
     else 
       getWithPrefix("s-", params, Array(QueryParam.LOCATION,QueryParam.CATEGORY))
  }
  def getWithPrefix(prefix:String,params: Map[QueryParam.Value, String],values:Array[QueryParam.Value]) : String = {
    for( v <- values)  {
      if(params.contains(v))
        prefix+params.getOrElse(v, "")+"/"
    }
    prefix
  }
  def getSuffix(params: Map[QueryParam.Value, String]) : String  = {
    "v1q0p"+ params.getOrElse(QueryParam.PAGE, "1")
  }
}