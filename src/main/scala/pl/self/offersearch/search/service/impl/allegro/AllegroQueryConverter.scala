package pl.self.offersearch.search.service.impl.allegro

import org.springframework.stereotype.Service

import pl.self.offersearch.search.query.model.QueryParam
import pl.self.offersearch.search.query.model.SimpleQuery
import pl.self.offersearch.search.service.QueryConverter
import pl.self.offersearch.consts.Consts

@Service(value=Consts.SpringBeans.ALLEGRO_CONVERTER)
class AllegroQueryConverter extends QueryConverter {

  val ALLEGRO_ENDPOINT = "https://allegro.pl/listing"
  val ALLEGRO_WITH_CATEGORY="https://allegro.pl/kategoria/"
  val DEFAULT_CATEGORY = "elektronika"
  val paramMap = Map(
      QueryParam.QUERY -> "string",
      QueryParam.PAGE -> "p",
      QueryParam.ORDER -> "order",
      QueryParam.LOCATION -> "city",
      QueryParam.REGION -> "city",
      QueryParam.DISTRICT -> "city"
     )
 
     
  def apply(queryParams: Map[QueryParam.Value, String]): SimpleQuery = {
     var query = if (queryParams.contains(QueryParam.CATEGORY))
         ALLEGRO_WITH_CATEGORY+queryParams.get(QueryParam.CATEGORY)
         .map { x=> x.toLowerCase }.getOrElse(DEFAULT_CATEGORY)
         else ALLEGRO_ENDPOINT
     var params = queryParams
                 .filter{ case(p,v) => p != QueryParam.CATEGORY }
                 .map { case(p,v) => (paramMap.getOrElse(p, "string"),v ) }
     new SimpleQuery(query,params)
  }
}