package com.jiwon.prestolabs.viewmodel

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.jiwon.prestolabs.api.WebSocket
import com.jiwon.prestolabs.models.Instrument
import com.jiwon.prestolabs.models.InstrumentParser
import com.jiwon.prestolabs.models.InstrumentUpdate
import com.jiwon.prestolabs.models.InstrumentUpdateParser
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class InstrumentViewModel @Inject constructor(
    val webSocket : WebSocket
) : ViewModel() {
    private val TAG = InstrumentViewModel::class.java.simpleName
    private val instrumentMap = HashMap<String, Instrument>()

    fun HashMap<String, Instrument>.add(instrument : Instrument){
        this[instrument.symbol] = instrument
    }

    // Update HashMap using newly received data from the websocket
    fun HashMap<String, Instrument>.update(update : InstrumentUpdate){
        val instrument = this[update.symbol]

        // check whether instrument exists
        // return if not
        instrument ?: return

        // update the stored instrument's value
        instrument.volume24 = update.volume24
        instrument.lastPrice = update.lastPrice
        instrument.lastChangePercentage = update.lastChangePercentage
    }

    private val gsonDecoder = GsonBuilder()
        .registerTypeAdapter(Instrument::class.java, InstrumentParser())
        .registerTypeAdapter(InstrumentUpdate::class.java, InstrumentUpdateParser())
        .create()

    fun connectSocket(){
        webSocket.openConnection(::onSocketMessageReceived)
    }

    fun onSocketMessageReceived(
        msg : String
    ) {
        // convert msg to JSON Object
        val jsonMsg = JSONObject(msg)

        // check whether json msg has action
        if(!jsonMsg.has(Action))
            return

        when(jsonMsg.get(Action)){
            // Initilize instruments
            Partial -> initializeInstruments(jsonMsg.getJSONArray(Data))
            // Update pre-existing instruments
            Update -> updateValues(jsonMsg.getJSONArray(Data))
        }
    }

    private fun initializeInstruments(array : JSONArray){
        for(index in 0 until array.length()){
            // parse string into instrument class
            val instrument = gsonDecoder.fromJson(array.getString(index), Instrument::class.java)

            // add instrument to the hash map
            instrumentMap.add(instrument)
        }
    }

    private fun updateValues(array : JSONArray){
        for(index in 0 until array.length()){
            // parse string into instrument update class
            val instrumentUpdate = gsonDecoder.fromJson(array.getString(index), InstrumentUpdate::class.java)

            // update internal values of the instrument
            instrumentMap.update(instrumentUpdate)
        }
    }

    companion object{
        const val Action = "action"
        const val Update = "update"
        const val Partial = "partial"
        const val Data = "data"
    }

}