package com.jiwon.prestolabs.model

import androidx.databinding.*

class InstrumentMap : HashMap<String, Instrument>(), ObservableMap<String, Instrument> {
    enum class Sorting{
        None,
        PriceDescending,
        PriceAscending,
        PercentChangeAscending,
        PercentChangeDescending,
        SymbolAscending,
        SymbolDescending,
        VolumeAscending,
        VolumeDescending
    }

    private val mapChangeRegistry = MapChangeRegistry()

    override fun addOnMapChangedCallback(callback: ObservableMap.OnMapChangedCallback<out ObservableMap<String, Instrument>, String, Instrument>?) {
        mapChangeRegistry.add(callback)
    }

    override fun removeOnMapChangedCallback(callback: ObservableMap.OnMapChangedCallback<out ObservableMap<String, Instrument>, String, Instrument>?) {
        mapChangeRegistry.remove(callback)
    }

    override fun clear() {
        val wasEmpty = isEmpty()
        if(!wasEmpty){
            super.clear()
            notifyChange(null)
        }
    }

    internal fun put(value : Instrument) : Instrument?{
        return put(value.symbol.get(), value)
    }

    override fun put(key: String?, value: Instrument): Instrument? {
        key?: return null
        if(value.state != InstrumentState.Open || value.isInverse) return null

        val value = super.put(key, value)
        notifyChange(value?.symbol?.get())
        return value
    }

    override fun remove(key: String): Instrument? {
        val value = super<HashMap>.remove(key)
        notifyChange(key)
        return value
    }

    override fun remove(key: String, value: Instrument): Boolean {
        val value = super<java.util.HashMap>.remove(key, value)
        notifyChange(key)
        return value
    }

    internal fun update(var1 : Instrument) : Instrument?{
        val value = get(var1.symbol.get())
        value ?: return null

        value.isInverse = var1.isInverse
        value.lastPrice.set(var1.lastPrice.get())
        value.lastChangePercentage.set(var1.lastChangePercentage.get())
        value.volume24.set(var1.volume24.get())
        notifyChange(var1.symbol.get())
        return value
    }

    @Synchronized
    internal fun updateAll(var1 : Array<InstrumentUpdate>){
        val iterator = var1.iterator()
        iterator.forEachRemaining {
            update(it)
        }
        notifyChange(null)
    }


    @Synchronized
    internal fun update(var1 : InstrumentUpdate) : Instrument?{
        val value = get(var1.symbol)
        value ?: return null

        var1.volume24?.let{
            value.volume24.set(it)
        }

        var1.lastChangePercentage?.let{
            value.lastChangePercentage.set(it)
        }

        var1.lastPrice?.let{
            value.lastPrice.set(it)
        }

        notifyChange(value.symbol.get())
        return value
    }

    internal fun get(value : Instrument) : Instrument?{
        return get(value.symbol.get())
    }

    override fun get(key: String): Instrument? {
        val value = super.get(key)
        notifyChange(value?.symbol?.get())
        return value
    }

    private fun notifyChange(key: String?) {
        mapChangeRegistry.notifyCallbacks(this, 0, key)
    }
}