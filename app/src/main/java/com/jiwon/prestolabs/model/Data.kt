package com.jiwon.prestolabs.model

import androidx.databinding.*
import com.google.gson.annotations.SerializedName
import java.util.Comparator

class Instrument(
    // Symbol is the key
    @Bindable @SerializedName("symbol") val symbol : ObservableField<String>,
    @Bindable @SerializedName("state") var state : InstrumentState,
    @Bindable @SerializedName("isInverse") var isInverse : Boolean = false,
    @Bindable @SerializedName("lastPrice") var lastPrice : ObservableInt = ObservableInt(0),
    @Bindable @SerializedName("lastChangePcnt") var lastChangePercentage : ObservableDouble = ObservableDouble(0.0),
    @Bindable @SerializedName("volume24h") var volume24 : ObservableLong = ObservableLong(0)
) : BaseObservable() {
    override fun toString(): String {
        return "symbol : ${symbol.get()}, state : $state, isInverse : ${isInverse}, lastPrice : ${lastPrice.get()}, lastChange : ${lastChangePercentage.get()} volume24 : ${volume24.get()}"
    }

    companion object{
        val SymbolComparator : Comparator<Instrument> = object : Comparator<Instrument>{
            override fun compare(var1: Instrument?, var2: Instrument?): Int {
                return when{
                    (var1 == null && var2 == null) -> 0
                    (var1 == null) -> -1
                    (var2 == null) -> 1
                    else -> var1.symbol.get()!!.compareTo(var2.symbol.get()!!)
                }
            }
        }

        val PriceComparator : Comparator<Instrument> = object : Comparator<Instrument>{
            override fun compare(var1: Instrument?, var2: Instrument?): Int {
                return when{
                    (var1 == null && var2 == null) -> 0
                    (var1 == null) -> -1
                    (var2 == null) -> 1
                    else -> var1.lastPrice.get().compareTo(var2.lastPrice.get())
                }
            }
        }

        val PercentageChangeCompator : Comparator<Instrument> = object : Comparator<Instrument>{
            override fun compare(var1: Instrument?, var2: Instrument?): Int {
                return when{
                    (var1 == null && var2 == null) -> 0
                    (var1 == null) -> -1
                    (var2 == null) -> 1
                    else -> var1.lastChangePercentage.get().compareTo(var2.lastChangePercentage.get())
                }
            }
        }

        val Volume24Compator : Comparator<Instrument> = object : Comparator<Instrument>{
            override fun compare(var1: Instrument?, var2: Instrument?): Int {
                return when{
                    (var1 == null && var2 == null) -> 0
                    (var1 == null) -> -1
                    (var2 == null) -> 1
                    else -> var1.volume24.get().compareTo(var2.volume24.get())
                }
            }
        }
    }
}

data class InstrumentUpdate(
    // Symbol is the key
    @SerializedName("symbol") val symbol : String,
    @SerializedName("lastPrice") var lastPrice : Int? = null,
    @SerializedName("lastChangePcnt") var lastChangePercentage : Double? = null,
    @SerializedName("volume24h") var volume24 : Long? = null
){
    override fun toString(): String {
        return "symbol : $symbol, lastprice : $lastPrice, lastChangePercentage : ${lastChangePercentage} volume24 : $volume24"
    }
}

enum class InstrumentState(
    val str : String
){
    Open("Open"),
    Closed("Closed");

    companion object{
        private val map = InstrumentState.values().associateBy(InstrumentState::str)
        fun get(value : String) = map.get(value)
    }
}



