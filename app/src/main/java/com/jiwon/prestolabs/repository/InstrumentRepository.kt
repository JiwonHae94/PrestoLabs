package com.jiwon.prestolabs.repository

import android.util.Log
import com.google.gson.GsonBuilder
import com.jiwon.prestolabs.api.WebSocket
import com.jiwon.prestolabs.model.*
import org.json.JSONArray
import org.json.JSONObject

class InstrumentRepository {
    private val TAG = InstrumentRepository::class.java.simpleName

    val webSocket = WebSocket()

    var instrumentMap : InstrumentMap? = null

    private val gsonDecoder = GsonBuilder()
        .registerTypeAdapter(Instrument::class.java, InstrumentParser())
        .registerTypeAdapter(InstrumentUpdate::class.java, InstrumentUpdateParser())
        .create()

    fun startSocket(instruments : InstrumentMap) {
        this.instrumentMap = instruments
        webSocket.openConnection(::onMessageReceived)
    }

    fun closeSocket() = webSocket.closeConnection()

    private fun initializeInstruments(array : JSONArray){
        for(index in 0 until array.length()){
            // parse string into instrument class
            val instrument = gsonDecoder.fromJson(array.getString(index), Instrument::class.java)

            // add instrument to the hash map
            instrumentMap?.put(instrument)
        }
    }

    private fun updateValues(array : JSONArray){
        val updateList = ArrayList<InstrumentUpdate>()

        for(index in 0 until array.length()){
            // parse string into instrument update class
            val instrumentUpdate = gsonDecoder.fromJson(array.getString(index), InstrumentUpdate::class.java)

            // add updates to the list
            updateList.add(instrumentUpdate)
        }

        // update internal values of the instrument
        instrumentMap?.updateAll(updateList.toTypedArray())
    }

    fun onMessageReceived(
        msg : String
    ) {
        // convert msg to JSON Object
        val jsonMsg = JSONObject(msg)

        // check whether json msg has action
        if(!jsonMsg.has(Action))
            return

        when(jsonMsg.get(Action)){
            // Initilize instruments
            Partial -> initializeInstruments(jsonMsg.getJSONArray(
                Data
            ))
            // Update pre-existing instruments
            Update -> updateValues(jsonMsg.getJSONArray(Data))
        }
    }

    companion object{
        const val Action = "action"
        const val Update = "update"
        const val Partial = "partial"
        const val Data = "data"

//        fun getDummyData() = InstrumentMap().apply{
//            put(Instrument(symbol = "A", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000000)))
//            put(Instrument(symbol = "B", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = true, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000000)))
//            put(Instrument(symbol = "C", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100000000000)))
//            put(Instrument(symbol = "D", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000000)))
//            put(Instrument(symbol = "E", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = true, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100000000000)))
//            put(Instrument(symbol = "F", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000)))
//            put(Instrument(symbol = "G", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 1000)))
//            put(Instrument(symbol = "H", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100000000000)))
//            put(Instrument(symbol = "I", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100000000000)))
//            put(Instrument(symbol = "J", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000000)))
//            put(Instrument(symbol = "K", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100000000000)))
//            put(Instrument(symbol = "L", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000000)))
//            put(Instrument(symbol = "M", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100000000000)))
//            put(Instrument(symbol = "N", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = true, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100000000000)))
//            put(Instrument(symbol = "O", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000000)))
//            put(Instrument(symbol = "P", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000)))
//            put(Instrument(symbol = "Q", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100000000)))
//            put(Instrument(symbol = "R", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 1000000)))
//            put(Instrument(symbol = "S", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000)))
//            put(Instrument(symbol = "T", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100)))
//        }
    }

}