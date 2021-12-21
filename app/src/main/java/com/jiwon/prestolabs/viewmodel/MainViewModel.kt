package com.jiwon.prestolabs.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayMap
import androidx.databinding.ObservableMap
import androidx.lifecycle.ViewModel
import com.jiwon.prestolabs.model.Instrument
import com.jiwon.prestolabs.model.InstrumentMap
import com.jiwon.prestolabs.repository.InstrumentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository : InstrumentRepository
) : ViewModel() {
    private val TAG = MainViewModel::class.java.simpleName

    // Instrument hashmap to receive data
    val observableInstruments = InstrumentMap()

    private val OnMapUpdated = object : ObservableMap.OnMapChangedCallback<ObservableMap<String, Instrument>, String, Instrument>(){
        override fun onMapChanged(sender: ObservableMap<String, Instrument>, key: String?) {

            // allow adapter in the recyclerview to be updated
            Log.d(TAG, "on map changed : "+ sender.entries.size.toString())
        }
    }
    
    fun connectSocket(){
        repository.startSocket(observableInstruments)
    }

    override fun onCleared() {
        super.onCleared()

        repository.closeSocket()
    }

    init{
        observableInstruments.addOnMapChangedCallback(OnMapUpdated)
    }

}