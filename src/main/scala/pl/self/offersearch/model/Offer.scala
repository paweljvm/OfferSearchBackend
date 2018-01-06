package pl.self.offersearch.model

import scala.beans.BeanProperty



case class Offer( @BeanProperty title: String,
    @BeanProperty img: String,
    @BeanProperty link: String,
    @BeanProperty place: String,
    @BeanProperty date:String ,
    @BeanProperty price: Double) {

  
  
}