package com.jiwon.prestolabs.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.jiwon.prestolabs.model.Instrument
import com.jiwon.prestolabs.view.adapter.InstrumentAdapter

object MainBindingAdapter {
    @BindingAdapter("app:sortBy")
    @JvmStatic fun sortInstrumentBy(
        view : TextView,
        attrsChange : InverseBindingAdapter
    ){

    }

}