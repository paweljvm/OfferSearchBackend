package pl.self.offersearch.search.service.impl.gumtree

import pl.self.offersearch.model.Offer
import pl.self.offersearch.search.query.model.Query
import pl.self.offersearch.search.service.SearchModule
import org.springframework.stereotype.Service
import pl.self.offersearch.search.query.model.SimpleQuery
import pl.self.offersearch.model.Result
import pl.self.offersearch.search.service.QueryConverter
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import pl.self.offersearch.consts.Consts
import pl.self.offersearch.consts.ModuleType


@Service
class GumtreeSearchModule (@Autowired  @Qualifier(Consts.SpringBeans.GUMTREE_CONVERTER) _queryConverter : QueryConverter) extends SearchModule {
  
  private object GumtreeSelectors {
    val OFFER =" li.result.pictures .result-link "
    val IMG =" #img-cnt img "
    val LINK =" .container .title a "
    val TITLE = LINK
    val PRICE =" .info .price .value .amount "
    val PLACE =" .container .category-location "
    val DATE = "  .container .info .creation-date span:last-child"
    val LAST_PAGE_LOTS = " .pagination .last.follows "
  }
  

  def queryConverter: QueryConverter =_queryConverter



  def getOfferSelectors: Map[String, (String, Element => String)] = {
    def parsePlace(e:Element) = {
      Option(e)
      .map {e => e.text() }
      .map {v => v.split(",") }
      .filter { a => a.length == 2 }
      .map { v => v(1).replace(" ", "")  }
     .getOrElse("")
    }
     Map(
        Consts.OfferFields.TITLE -> ( GumtreeSelectors.TITLE,text),
        Consts.OfferFields.IMG -> (GumtreeSelectors.IMG, e => attr(e,"src")),
        Consts.OfferFields.LINK -> (GumtreeSelectors.LINK,e => attr(e,"href")),
        Consts.OfferFields.PLACE -> (GumtreeSelectors.PLACE,parsePlace ),
        Consts.OfferFields.DATE -> (GumtreeSelectors.DATE,text),
        Consts.OfferFields.PRICE -> (GumtreeSelectors.PRICE,text)
     )
  }

  def getMainSelector: String = {
     GumtreeSelectors.OFFER
  }

  def pages(document: Document): Int = {
    def parsePages(link:String) = {
      val pageIndex =  link.indexOf("page")
      link.substring(pageIndex+5, link.indexOf("/",pageIndex))
    }
    property(GumtreeSelectors.LAST_PAGE_LOTS,document,e => parsePages(attr(e,"href"))).toInt
  }
   def moduleType: ModuleType.Value = ModuleType.GUMTREE
}