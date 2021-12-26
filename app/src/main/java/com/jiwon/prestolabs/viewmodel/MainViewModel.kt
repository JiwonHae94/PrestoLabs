package com.jiwon.prestolabs.viewmodel

import android.util.Log
import androidx.databinding.ObservableMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    val currentSorting = MutableLiveData(InstrumentMap.Sorting.VolumeDescending)

    fun updateSorting(
        sorting : InstrumentMap.Sorting
    ){
        Log.d(TAG, "sorting updated : ${sorting}")
        currentSorting.postValue(sorting)
    }

    fun sortByPricePercentChange(){
        // check whether current sorting is either percent change ascending or descending
        val sorting = when(currentSorting.value ?: InstrumentMap.Sorting.None){

            // which sorting in the order of price decending -> price ascending -> change descending -> change ascending
            InstrumentMap.Sorting.PriceDescending -> InstrumentMap.Sorting.PriceAscending
            InstrumentMap.Sorting.PriceAscending -> InstrumentMap.Sorting.PercentChangeDescending
            InstrumentMap.Sorting.PercentChangeDescending -> InstrumentMap.Sorting.PercentChangeAscending
            InstrumentMap.Sorting.PercentChangeAscending -> InstrumentMap.Sorting.PriceDescending

            // if otherwise apply price ascending
            else -> InstrumentMap.Sorting.PriceDescending
        }
        updateSorting(sorting)
    }

    fun sortByVolume(){
        // check whether current sorting is either volume ascending or descending
        val sorting = when(currentSorting.value ?: InstrumentMap.Sorting.None){
            InstrumentMap.Sorting.VolumeAscending -> InstrumentMap.Sorting.VolumeDescending
            InstrumentMap.Sorting.VolumeDescending -> InstrumentMap.Sorting.VolumeAscending

            // if otherwise apply percent ascending
            else -> InstrumentMap.Sorting.VolumeDescending
        }
        updateSorting(sorting)
    }

    fun sortBySymbol(){
        // check whether current sorting is either symbol ascending or descending
        val sorting = when(currentSorting.value ?: InstrumentMap.Sorting.None){
            InstrumentMap.Sorting.SymbolAscending -> InstrumentMap.Sorting.SymbolDescending
            InstrumentMap.Sorting.SymbolDescending -> InstrumentMap.Sorting.SymbolAscending

            // if otherwise apply symbol ascending
            else -> InstrumentMap.Sorting.SymbolDescending
        }
        updateSorting(sorting)
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