package com.jiwon.prestolabs.viewmodel

import androidx.databinding.ObservableMap
import androidx.lifecycle.ViewModel
import com.jiwon.prestolabs.BR
import com.jiwon.prestolabs.model.Instrument
import com.jiwon.prestolabs.model.InstrumentHashMap
import com.jiwon.prestolabs.repository.InstrumentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.internal.notify
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository : InstrumentRepository
) : ViewModel() {
    private val TAG = MainViewModel::class.java.simpleName
    val instruments = repository.instrumentHashMap

    private val OnMapUpdated = object : ObservableMap.OnMapChangedCallback<ObservableMap<String, Instrument>, String, Instrument>(){
        override fun onMapChanged(sender: ObservableMap<String, Instrument>?, key: String?) {
            // allow adapter in the recyclerview to be updated

        }
    }

    override fun onCleared() {
        super.onCleared()

        repository.closeSocket()
    }

    init{
        repository.startSocket()
        instruments.updateOnMapChangedCallback(OnMapUpdated)
    }

    companion object{
        const val Action = "action"
        const val Update = "update"
        const val Partial = "partial"
        const val Data = "data"
    }
}