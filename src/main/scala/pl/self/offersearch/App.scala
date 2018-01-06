package pl.self.offersearch

import org.jsoup.Jsoup

import scala.collection.JavaConverters._
import pl.self.offersearch.search.service.impl.olx.OlxSearchModule
import pl.self.offersearch.search.service.impl.olx.OlxQueryConverter
import pl.self.offersearch.search.query.model.QueryParam
import pl.self.offersearch.search.service.impl.gumtree.GumtreeSearchModule
import pl.self.offersearch.search.service.impl.gumtree.GumtreeQueryConverter
import pl.self.offersearch.search.service.impl.allegro.AllegroSearchModule
import pl.self.offersearch.search.service.impl.allegro.AllegroQueryConverter

/**
 * @author ${user.name}
 */
object App {
  

  
  def main(args : Array[String]) {
    for( module <- Array(
        new OlxSearchModule( new OlxQueryConverter),
        new GumtreeSearchModule(new GumtreeQueryConverter) ,
        new AllegroSearchModule(new AllegroQueryConverter))
              ) {
       val   result =  module.search(Map(QueryParam.QUERY -> "nubira"))
       println(result)
    }

    
    
  }

}
