package com.jiwon.prestolabs.model

import com.google.gson.annotations.SerializedName

data class Instrument(
    // Symbol is the key
    @SerializedName("symbol") val symbol : String,
    @SerializedName("state") var state : InstrumentState,
    @SerializedName("isInverse") var isInverse : Boolean,
    @SerializedName("lastPrice") var lastPrice : Int = 0,
    @SerializedName("lastChangePcnt") var lastChangePercentage : Double = 0.0,
    @SerializedName("volume24h") var volume24 : Int = 0
){
    override fun toString(): String {
        return "symbol : $symbol, state : $state, isInverse : ${isInverse}"
    }

    companion object{
        fun CompareByLastPrice(
            var1 : Instrument?,
            var2 : Instrument?
        ) : Int{
            return when{
                (var1 == null && var2 == null) -> 0
                (var1 == null) -> -1
                (var2 == null) -> 1
                else -> var1.lastPrice.compareTo(var2.lastPrice)
            }
        }

        fun CompareByLastChangedPercentage(
            var1 : Instrument?,
            var2 : Instrument?
        ) : Int{
            return when{
                (var1 == null && var2 == null) -> 0
                (var1 == null) -> -1
                (var2 == null) -> 1
                else -> var1.lastChangePercentage.compareTo(var2.lastChangePercentage)
            }
        }
    }
}

data class InstrumentUpdate(
    // Symbol is the key
    @SerializedName("symbol") val symbol : String,
    @SerializedName("lastPrice") var lastPrice : Int = 0,
    @SerializedName("lastChangePcnt") var lastChangePercentage : Double = 0.0,
    @SerializedName("volume24h") var volume24 : Int = 0
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



