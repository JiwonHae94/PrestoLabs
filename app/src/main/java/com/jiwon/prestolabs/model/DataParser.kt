package com.jiwon.prestolabs.model

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
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
        val jsonObject = json.asJsonObject

        // get parse information from the data and instatiate an instrument class
        return try{
            Instrument(
                symbol = ObservableField(jsonObject.get(Symbol).asString),
                isInverse = jsonObject.get(IsInverse).asBoolean,
                state = InstrumentState.get(jsonObject.get(State).asString) ?: InstrumentState.Closed
            )
        }catch(e: JSONException){
            Log.e(TAG, "failed to parse Instrument due to ${json} ${e.localizedMessage}")
            null
        }
    }

    companion object{
        private const val Symbol = "symbol"
        private const val IsInverse = "isInverse"
        private const val State = "state"
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
        val jsonObject = json.asJsonObject

        // get parse information from the data and instatiate an instrument class
        return try{
            InstrumentUpdate(
                symbol = jsonObject.get(Symbol).asString
            ).apply{
                if(jsonObject.has(LastChangePercent)){
                    this.lastChangePercentage = try{
                        jsonObject.get(LastChangePercent).asDouble
                    }catch(e: Exception){
                        Log.w(TAG, "failed to parse last change percentage : " + jsonObject)
                        null
                    }
                }

                if(jsonObject.has(LastPrice)){
                    this.lastPrice = try{
                        jsonObject.get(LastPrice).asInt
                    }catch(e: Exception){
                        Log.w(TAG, "failed to parse last price : " + jsonObject)
                        null
                    }
                }

                if(jsonObject.has(Volume24h)){
                    this.volume24 = try{
                        jsonObject.get(Volume24h).asLong
                    }catch(e: Exception){
                        Log.w(TAG, "failed to parse volume 24 : " + jsonObject)
                        null
                    }
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