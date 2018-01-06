package pl.self.offersearch.model

import scala.beans.BeanProperty

import collection.JavaConverters._

case class Result(@BeanProperty pages: Int,@BeanProperty offers: List[Offer]) {

}