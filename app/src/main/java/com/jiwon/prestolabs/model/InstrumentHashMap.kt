package com.jiwon.prestolabs.model

import androidx.databinding.ObservableMap
import com.jiwon.prestolabs.model.Instrument.Companion.CompareByLastChangedPercentage
import com.jiwon.prestolabs.model.Instrument.Companion.CompareByLastPrice

class InstrumentHashMap(
    private var onAddInstrumentCallback: ObservableMap.OnMapChangedCallback<out ObservableMap<String, Instrument>, String, Instrument>? = null,
    private var onUpdateInstrumentCallback: ObservableMap.OnMapChangedCallback<out ObservableMap<String, Instrument>, String, Instrument>? = null,
    private var onRemoveInstrumentCallback: ObservableMap.OnMapChangedCallback<out ObservableMap<String, Instrument>, String, Instrument>? = null
) : ObservableMap<String, Instrument> {
    
    enum class Sorting(val comparator : (Instrument, Instrument) -> Int,) {
        PriceAscending(::CompareByLastPrice),
        PriceDescending(::CompareByLastPrice),
        ChangeAscending(::CompareByLastChangedPercentage),
        ChangeDescending(::CompareByLastChangedPercentage)
    }

    enum class Actions(val value : Int){
        Add(1), Remove(2), Update(3)
    }

    private val instrumentHashMap = HashMap<String, Instrument>()

    override val size: Int = instrumentHashMap.keys.size

    override fun containsKey(key: String): Boolean {
        return instrumentHashMap.containsKey(key)
    }

    override fun containsValue(value: Instrument): Boolean {
        return instrumentHashMap.containsValue(value)
    }

    override fun get(key: String): Instrument? {
        return instrumentHashMap.get(key)
    }

    override fun isEmpty(): Boolean {
        return instrumentHashMap.isEmpty()
    }

    override val entries: MutableSet<MutableMap.MutableEntry<String, Instrument>>
        get() = instrumentHashMap.entries

    override val keys: MutableSet<String>
        get() = instrumentHashMap.keys

    override val values: MutableCollection<Instrument>
        get() = instrumentHashMap.values

    override fun clear() {
        instrumentHashMap.clear()
    }

    fun update(value : Instrument) : Instrument? {
        // get previous instrument
        val previous = get(value)

        // check whether instrument exists
        // return if not
        previous ?: return null

        // update the stored instrument's value
        previous.volume24 = value.volume24
        previous.lastPrice = value.lastPrice
        previous.lastChangePercentage = value.lastChangePercentage
        return previous
    }

    fun update(value : InstrumentUpdate) : Instrument?{
        // get previous instrument
        val previous = get(value.symbol)

        // check whether instrument exists
        // return if not
        previous ?: return null

        // update the stored instrument's value
        previous.volume24 = value.volume24
        previous.lastPrice = value.lastPrice
        previous.lastChangePercentage = value.lastChangePercentage
        return previous
    }

    fun put(value : Instrument) : Instrument?{
        return put(value.symbol, value)
    }

    override fun put(key: String, value: Instrument): Instrument? {
        // put new value to the map
        val rslt = instrumentHashMap.put(key, value)

        if(rslt != null){
            // notify listeners if they're available
            onAddInstrumentCallback?.onMapChanged(null, key)
            onUpdateInstrumentCallback?.onMapChanged(null, key)
        }

        return rslt
    }

    fun get(value : Instrument) : Instrument?{
        return get(value.symbol)
    }

    override fun putAll(from: Map<out String, Instrument>) {
        // put all elemenst within the map to the instrumentHashMap
        instrumentHashMap.putAll(from)

        if(from.isNotEmpty()){

            // notify listeners if they're available
            onAddInstrumentCallback?.onMapChanged(null, from.keys.first())
            onUpdateInstrumentCallback?.onMapChanged(null, from.keys.first())
        }
    }

    fun remove(value: Instrument): Instrument? {
        // remove element by the symbol
        return remove(value.symbol)
    }

    override fun remove(key: String): Instrument? {
        // remove element by key
        val rslt = instrumentHashMap.remove(key)

        if(rslt != null){
            // notify listeners if they're available
            onRemoveInstrumentCallback?.onMapChanged(null, key)
            onUpdateInstrumentCallback?.onMapChanged(null, key)
        }

        return rslt
    }

    override fun addOnMapChangedCallback(callback: ObservableMap.OnMapChangedCallback<out ObservableMap<String, Instrument>, String, Instrument>) {
        onAddInstrumentCallback = callback
    }

    override fun removeOnMapChangedCallback(callback: ObservableMap.OnMapChangedCallback<out ObservableMap<String, Instrument>, String, Instrument>) {
        onRemoveInstrumentCallback = callback
    }

    fun updateOnMapChangedCallback(callback : ObservableMap.OnMapChangedCallback<out ObservableMap<String, Instrument>, String, Instrument>) {
        onUpdateInstrumentCallback = callback
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

