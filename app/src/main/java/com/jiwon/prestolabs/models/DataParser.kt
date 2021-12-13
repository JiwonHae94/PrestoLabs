package com.jiwon.prestolabs.models

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.json.JSONException
import java.lang.reflect.Type

class InstrumentParser : JsonDeserializer<Instrument?> {
    private val TAG = Instrument::class.java.simpleName

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Instrument? {
        // convert json as JSONObject
        val jsonObject = json.asJsonObject.getAsJsonObject("data")

        // get parse information from the data and instatiate an instrument class
        return try{
            Instrument(
                symbol = jsonObject.get(Symbol).asString,
                isInverse =  jsonObject.get(IsInverse).asBoolean,
                state = InstrumentState.get(jsonObject.get(State).asString) ?: InstrumentState.Closed
            )
        }catch(e: JSONException){
            Log.e(TAG, "failed to parse Instrument due to ${e.localizedMessage}")
            null
        }
    }

    companion object{
        private const val Symbol = "symbol"
        private const val IsInverse = "isInverse"
        private const val State = "State"
    }
}


class InstrumentUpdateParser : JsonDeserializer<InstrumentUpdate?>{
    private val TAG = InstrumentUpdate::class.java.simpleName

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext?
    ): InstrumentUpdate? {
        // convert json as JSONObject
        val jsonObject = json.asJsonObject.getAsJsonObject("data")

        // get parse information from the data and instatiate an instrument class
        return try{
            InstrumentUpdate(
                symbol = jsonObject.get(Symbol).asString,
                lastChangePercentage = jsonObject.get(LastChangePercent).asDouble
            ).apply{
                if(jsonObject.has(LastPrice)){
                    this.lastPrice = jsonObject.get(LastPrice).asInt
                }

                if(jsonObject.has(Volume24h)){
                    this.volume24 = jsonObject.get(Volume24h).asInt
                }
            }


        }catch(e: JSONException){
            Log.e(TAG, "failed to parse InstrumentUpdate due to ${e.localizedMessage}")
            return null
        }
    }

    companion object{
        private const val Symbol = "symbol"
        private const val LastPrice = "lastPrice"
        private const val LastChangePercent = "lastChangePcnt"
        private const val Volume24h = "volume24h"
    }
}