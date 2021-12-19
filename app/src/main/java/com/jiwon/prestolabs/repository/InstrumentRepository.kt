package com.jiwon.prestolabs.repository

import com.google.gson.GsonBuilder
import com.jiwon.prestolabs.api.WebSocket
import com.jiwon.prestolabs.model.Instrument
import com.jiwon.prestolabs.model.InstrumentParser
import com.jiwon.prestolabs.model.InstrumentUpdate
import com.jiwon.prestolabs.model.InstrumentUpdateParser
import com.jiwon.prestolabs.viewmodel.InstrumentViewModel
import org.json.JSONArray
import org.json.JSONObject

class InstrumentRepository {
    val webSocket = WebSocket()
    private val instrumentMap = HashMap<String, Instrument>()

    private val gsonDecoder = GsonBuilder()
        .registerTypeAdapter(Instrument::class.java, InstrumentParser())
        .registerTypeAdapter(InstrumentUpdate::class.java, InstrumentUpdateParser())
        .create()

    fun startSocket() = webSocket.openConnection(::onMessageReceived)

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

    fun onMessageReceived(
        msg : String
    ) {
        // convert msg to JSON Object
        val jsonMsg = JSONObject(msg)

        // check whether json msg has action
        if(!jsonMsg.has(InstrumentViewModel.Action))
            return

        when(jsonMsg.get(InstrumentViewModel.Action)){
            // Initilize instruments
            InstrumentViewModel.Partial -> initializeInstruments(jsonMsg.getJSONArray(
                InstrumentViewModel.Data
            ))
            // Update pre-existing instruments
            InstrumentViewModel.Update -> updateValues(jsonMsg.getJSONArray(InstrumentViewModel.Data))
        }
    }
}