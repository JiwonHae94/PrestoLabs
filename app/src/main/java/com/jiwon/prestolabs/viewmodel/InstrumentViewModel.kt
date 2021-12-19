package com.jiwon.prestolabs.viewmodel

import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.jiwon.prestolabs.api.WebSocket
import com.jiwon.prestolabs.model.Instrument
import com.jiwon.prestolabs.model.InstrumentParser
import com.jiwon.prestolabs.model.InstrumentUpdate
import com.jiwon.prestolabs.model.InstrumentUpdateParser
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class InstrumentViewModel @Inject constructor(
    val webSocket : WebSocket
) : ViewModel() {
    private val TAG = InstrumentViewModel::class.java.simpleName



    companion object{
        const val Action = "action"
        const val Update = "update"
        const val Partial = "partial"
        const val Data = "data"
    }
}