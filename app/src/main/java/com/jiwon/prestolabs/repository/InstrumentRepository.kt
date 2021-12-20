package com.jiwon.prestolabs.repository

import android.util.Log
import androidx.databinding.ObservableMap
import com.google.gson.GsonBuilder
import com.jiwon.prestolabs.api.WebSocket
import com.jiwon.prestolabs.model.*
import com.jiwon.prestolabs.viewmodel.MainViewModel
import org.json.JSONArray
import org.json.JSONObject
import kotlin.random.Random

class InstrumentRepository {
    private val TAG = InstrumentRepository::class.java.simpleName

    val webSocket = WebSocket()

    val instrumentHashMap = InstrumentHashMap()

    private val gsonDecoder = GsonBuilder()
        .registerTypeAdapter(Instrument::class.java, InstrumentParser())
        .registerTypeAdapter(InstrumentUpdate::class.java, InstrumentUpdateParser())
        .create()

    fun startSocket() = webSocket.openConnection(::onMessageReceived)

    fun closeSocket() = webSocket.closeConnection()

    private fun initializeInstruments(array : JSONArray){
        Log.d(TAG, "initailize instruments : $array")
        for(index in 0 until array.length()){
            // parse string into instrument class
            val instrument = gsonDecoder.fromJson(array.getString(index), Instrument::class.java)

            // add instrument to the hash map
            instrumentHashMap.put(instrument)
        }
    }

    private fun updateValues(array : JSONArray){
        //Log.d(TAG, "updateValues instruments : $array")

        for(index in 0 until array.length()){
            // parse string into instrument update class
            val instrumentUpdate = gsonDecoder.fromJson(array.getString(index), InstrumentUpdate::class.java)

            // update internal values of the instrument
            instrumentHashMap.update(instrumentUpdate)
        }
    }

    fun onMessageReceived(
        msg : String
    ) {
        // convert msg to JSON Object
        val jsonMsg = JSONObject(msg)

        // check whether json msg has action
        if(!jsonMsg.has(MainViewModel.Action))
            return

        when(jsonMsg.get(MainViewModel.Action)){
            // Initilize instruments
            MainViewModel.Partial -> initializeInstruments(jsonMsg.getJSONArray(
                MainViewModel.Data
            ))
            // Update pre-existing instruments
            MainViewModel.Update -> updateValues(jsonMsg.getJSONArray(MainViewModel.Data))
        }
    }

    companion object{
        fun getDummyData() = InstrumentHashMap().apply{
            put(Instrument(symbol = "A", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000000)))
            put(Instrument(symbol = "B", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = true, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000000)))
            put(Instrument(symbol = "C", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100000000000)))
            put(Instrument(symbol = "D", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000000)))
            put(Instrument(symbol = "E", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = true, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100000000000)))
            put(Instrument(symbol = "F", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000)))
            put(Instrument(symbol = "G", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 1000)))
            put(Instrument(symbol = "H", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100000000000)))
            put(Instrument(symbol = "I", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100000000000)))
            put(Instrument(symbol = "J", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000000)))
            put(Instrument(symbol = "K", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100000000000)))
            put(Instrument(symbol = "L", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000000)))
            put(Instrument(symbol = "M", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100000000000)))
            put(Instrument(symbol = "N", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = true, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100000000000)))
            put(Instrument(symbol = "O", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000000)))
            put(Instrument(symbol = "P", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000)))
            put(Instrument(symbol = "Q", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100000000)))
            put(Instrument(symbol = "R", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 1000000)))
            put(Instrument(symbol = "S", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 10000)))
            put(Instrument(symbol = "T", state = InstrumentState.Open, lastPrice = Random.nextInt(0, 10000000), isInverse = false, lastChangePercentage = Random.nextDouble(-100.0, 100.0), volume24 = Random.nextLong(0, 100)))
        }
    }

}