package pl.self.offersearch.consts

object Consts {
  
  object Package {
    final val BASE = "pl.self.offersearch"
  }
  object Mappings {
    object Search {
      final val SEARCH = "/search"
    }
  }
  object RequestParams {
    final val QUERY = "query"
    final val TYPE ="type"
  }
  object SpringBeans {
    final val OLX_CONVERTER="olxQueryConverter"
    final val ALLEGRO_CONVERTER="allegroQueryConverter"
    final val GUMTREE_CONVERTER="gumtreeQueryConverter"
  }
  object OfferFields {
    final val TITLE = "title"
    final val LINK = "link"
    final val IMG ="img"
    final val PLACE="place"
    final val DATE ="date"
    final val PRICE="price"
  }

}