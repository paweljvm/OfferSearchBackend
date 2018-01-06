package pl.self.offersearch.consts

object ModuleType extends Enumeration {
    val ALLEGRO =Value("allegro")
    val OLX = Value("olx")
    val GUMTREE = Value("gumtree")
    
    def valueOf(v:String) : ModuleType.Value = {
      val result = ModuleType.values.filter { value  => value.toString().equalsIgnoreCase(v) }
      return if(result.isEmpty) ModuleType.OLX else result.firstKey
    }
}