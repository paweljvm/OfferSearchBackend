package pl.self.offersearch.search.service

import pl.self.offersearch.model.Offer
import pl.self.offersearch.search.query.model.Query
import pl.self.offersearch.search.query.model.SimpleQuery
import pl.self.offersearch.model.Result
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import collection.JavaConverters._
import pl.self.offersearch.search.query.model.QueryParam
import org.jsoup.nodes.Element
import pl.self.offersearch.consts.Consts
import java.io.File
import java.io.FileWriter
import pl.self.offersearch.consts.ModuleType

trait SearchModule {
  
  
  
  def search(params: Map[QueryParam.Value,String]): Result = {
    val query = queryConverter(params)
    val document = Jsoup.connect(query.endpoint)
   .data(query.params.asJava)
   .get()
   parseDocument(document)
  }

  
  
  def parseDocument(document:Document) : Result = {
    val selector  =getMainSelector
    val offers =  document.select(selector)
     new Result(pages(document), 
          offers
           .asScala
           .toList.map(element2Offer) )
  }
  
  def text(element:Element) = Option(element) . map { e => e.text }.getOrElse("")
  
  def attr(element:Element,attrName:String) : String = {
    Option(element).map { e => e.attr(attrName)}.getOrElse("")
  }
  
 def element2Offer(element:Element) : Offer = {
    
     val offerSelectors = getOfferSelectors
    
    def prop(selectorId:String) : String = {
      var selector = offerSelectors.getOrElse(selectorId, { ("",null) })
      
      return property(selector._1,element,selector._2)
    }
    return new Offer( 
        prop(Consts.OfferFields.TITLE),
        prop(Consts.OfferFields.IMG),
        prop(Consts.OfferFields.LINK),
        prop(Consts.OfferFields.PLACE),
        prop(Consts.OfferFields.DATE),
        parsePrice(prop(Consts.OfferFields.PRICE))
        )
  }
  def property(selector:String,element:Element,valueRetreiver:(Element) => (String)) : String =  {
    if(selector.isEmpty())
      return ""
    val value =  element.select(selector).first()
    valueRetreiver(value)
  }
  def getOfferSelectors : Map[String,(String,(Element) => String)]
 
  def pages(document:Document) : Int
  
  def getMainSelector : String
  
  def parsePrice(price:String) : Double =  {
    try {
      price
      .replace("zł", "")
      .replace(",",".")
      .replace(" ","")
      .replace(",",".")
      .replace("&nbsp","")
      .toDouble
    } catch {
      case e : Exception => -1d
    }
     
  }
  
  def queryConverter : QueryConverter
  
  def moduleType : ModuleType.Value
}