package pl.self.offersearch.search.controller

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import pl.self.offersearch.consts.Consts
import pl.self.offersearch.model.Result
import pl.self.offersearch.search.service.impl.olx.OlxSearchModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestParam
import pl.self.offersearch.search.service.impl.olx.OlxQueryConverter
import pl.self.offersearch.search.query.model.QueryParam
import pl.self.offersearch.search.service.SearchModule
import com.google.gson.Gson
import pl.self.offersearch.model.Offer
import collection.JavaConverters._
import javax.annotation.PostConstruct
import pl.self.offersearch.consts.Consts
import pl.self.offersearch.consts.ModuleType
import pl.self.offersearch.model.SearchResults

@RestController
class SearchController(@Autowired
   
    val searchModules: java.util.List[SearchModule]
    ) {
  
  var searchModulesMap: Map[ModuleType.Value,SearchModule] = Map()
  var searchModulesScala:List[SearchModule] = List()
  
  @PostConstruct
  def init() {
    searchModulesScala =  searchModules.asScala.toList
    searchModulesMap =searchModulesScala.map { s => s.moduleType -> s } .toMap 
  }
  
  
  @GetMapping(path = Array(Consts.Mappings.Search.SEARCH) )
  def search(@RequestParam(Consts.RequestParams.QUERY) query:String,@RequestParam(value=Consts.RequestParams.TYPE ,required = false) moduleTypeParam:String) : SearchResults = {
   
    var moduleType = Option(moduleTypeParam).map { v => ModuleType.valueOf(v) }.getOrElse(ModuleType.OLX)
    val searchModule:SearchModule = searchModulesMap
    .getOrElse(moduleType,searchModulesScala.filter {  s => s.moduleType == ModuleType.OLX }(0) )
    val result= searchModule.search(Map(QueryParam.QUERY -> query))
    
    return new SearchResults(result.offers.asJava,result.pages);
  }
}