package com.jiwon.prestolabs.repository

import android.util.Log
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.databinding.ObservableLong
import com.google.gson.GsonBuilder
import com.jiwon.prestolabs.api.WebSocket
import com.jiwon.prestolabs.model.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

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

            // Log.d
            Log.i(TAG, "update : ${instrumentUpdate}")

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

        fun getDummyData() = InstrumentMap().apply {
            for (i in 0 until 50000) {
                put(
                    Instrument(
                        symbol = ObservableField(i.toString()),
                        state = InstrumentState.Open,
                        lastPrice = ObservableInt(Random.nextInt(0, 10000000)),
                        isInverse = false,
                        lastChangePercentage = ObservableDouble(Random.nextDouble(-100.0, 100.0)),
                        volume24 = ObservableLong(Random.nextLong(0, 10000000))
                    )
                )
            }
        }
    }

}