package pl.self.offersearch.search.service.impl.allegro

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

import pl.self.offersearch.consts.Consts
import pl.self.offersearch.search.service.QueryConverter
import pl.self.offersearch.search.service.SearchModule
import pl.self.offersearch.consts.ModuleType

@Service
class AllegroSearchModule(@Autowired @Qualifier(Consts.SpringBeans.ALLEGRO_CONVERTER) _queryConverter: QueryConverter) extends SearchModule {
  
  private object AllegroSelectors {
    val OFFER = " ._433675f "
    val IMG = OFFER +" img"
    val LINK = OFFER +"  ._342830a > a "
    val TITLE = LINK
    val PRICE = OFFER +" ._7765cc3 ._1245c7d strong"
    val LAST_PAGE = ".pagination-top .quantity > a "
    val PLACE = ""
    val DATE = ""
    
  }

  def queryConverter: QueryConverter =_queryConverter

  

  def getOfferSelectors: Map[String, (String, Element => String)] = {
     Map(
        Consts.OfferFields.TITLE -> ( AllegroSelectors.TITLE,text),
        Consts.OfferFields.IMG -> (AllegroSelectors.IMG, e => attr(e,"src")),
        Consts.OfferFields.LINK -> (AllegroSelectors.LINK,e => attr(e,"href")),
        Consts.OfferFields.PLACE -> (AllegroSelectors.PLACE,e => ""),
        Consts.OfferFields.DATE -> (AllegroSelectors.DATE,e => ""),
        Consts.OfferFields.PRICE -> (AllegroSelectors.PRICE,text)
     )
  }

  def getMainSelector: String = {
     AllegroSelectors.OFFER
  }

  def pages(document: Document): Int = {
    property(AllegroSelectors.LAST_PAGE,document,text).toInt
  }
  def moduleType: ModuleType.Value = ModuleType.ALLEGRO
}