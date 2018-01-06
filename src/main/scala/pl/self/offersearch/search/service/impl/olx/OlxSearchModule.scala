package pl.self.offersearch.search.service.impl.olx

import pl.self.offersearch.search.service.SearchModule
import org.springframework.stereotype.Service
import pl.self.offersearch.model.Offer
import pl.self.offersearch.search.query.model.Query
import org.jsoup.Jsoup
import pl.self.offersearch.search.query.model.SimpleQuery

import collection.JavaConverters._
import pl.self.offersearch.model.Result
import org.jsoup.nodes.Element
import org.jsoup.nodes.Document
import pl.self.offersearch.search.service.QueryConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import pl.self.offersearch.consts.Consts
import pl.self.offersearch.consts.ModuleType

@Service
class OlxSearchModule( @Autowired  @Qualifier(Consts.SpringBeans.OLX_CONVERTER)  _queryConverter : QueryConverter) extends SearchModule {
  
   private object OlxSelectors {
     val OLX_OFFER=".offer"
     val IMG= OLX_OFFER +" a.thumb img.fleft "
     val LINK = OLX_OFFER +" div.space.rel h3 > a "
     val TITLE =LINK +"  > strong "
     val PRICE = OLX_OFFER +" .price > strong "
     val PLACE = OLX_OFFER +" div.space.rel small.breadcrumb span "
     val DATE = OLX_OFFER +" tr:last-child div.space.rel p:last-child "
     val LAST_PAGE_LOTS = ".pager.rel.clr span.block.fleft + span.item.fleft span"
     val LAST_PAGE_MIN = ".pager.rel.clr span.item.fleft"
  
  }

 def pages(document:Document) : Int = {

    def helper(element:Element) : String = {
      Option(element)
      .orElse(Option(document.select(OlxSelectors.LAST_PAGE_MIN).first()))
      .map { el => el.text()}
      .getOrElse("1")
    }
    
   property(OlxSelectors.LAST_PAGE_LOTS,document,helper).replace(" ","").toInt
   
  }
  
  

  def queryConverter = _queryConverter

  def getOfferSelectors: Map[String, (String, Element => String)] = {
     Map(
        Consts.OfferFields.TITLE -> ( OlxSelectors.TITLE,text),
        Consts.OfferFields.IMG -> (OlxSelectors.IMG, e => attr(e,"src")),
        Consts.OfferFields.LINK -> (OlxSelectors.LINK,e => attr(e,"href")),
        Consts.OfferFields.PLACE -> (OlxSelectors.PLACE,text),
        Consts.OfferFields.DATE -> (OlxSelectors.DATE,text),
        Consts.OfferFields.PRICE -> (OlxSelectors.PRICE,text)
     )
   }


  def getMainSelector: String = {
     OlxSelectors.OLX_OFFER
   }
  def moduleType: ModuleType.Value = ModuleType.OLX

}