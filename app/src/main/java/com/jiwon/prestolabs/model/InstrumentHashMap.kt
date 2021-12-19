package com.jiwon.prestolabs.model

class InstrumentHashMap : HashMap<String, Instrument>() {
    fun get(value : Instrument) : Instrument?{
        return get(value.symbol)
    }

    fun add(value : Instrument) : Instrument?{
        return put(value.symbol, value)
    }

}

fun InstrumentHashMap.sortedMapBy(
    selector : (Instrument, Instrument) -> Int,
    reverse : Boolean = false
) : List<Instrument>{
    return if(reverse){
        values.sortedWith(selector).reversed()
    }else{
        values.sortedWith(selector)
    }
}

