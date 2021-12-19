package com.jiwon.prestolabs.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.jiwon.prestolabs.model.InstrumentHashMap

class InstrumentViewModel : BaseObservable() {
    private var currentClickCount : Int = 0

    private var clickCount : Int
        @Bindable get(){
            return currentClickCount
        }
        set(value){
            currentClickCount = value
        }

    private var _currentSorting = InstrumentHashMap.Sorting.PriceAscending

    var currentSorting : InstrumentHashMap.Sorting
        @Bindable get(){
            return currentSorting
        }
        set(value){
            // set the value
            _currentSorting = value

            // notifies ui for two way binding
            //notifyPropertyChanged()
        }
}