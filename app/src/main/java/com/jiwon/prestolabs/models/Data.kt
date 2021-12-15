package com.jiwon.prestolabs.models

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



