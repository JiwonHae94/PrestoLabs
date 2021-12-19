package com.jiwon.prestolabs.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter

object MainBindingAdapter {
    @BindingAdapter("app:sortBy")
    @JvmStatic fun sortInstrumentBy(
        view : TextView,
        attrsChange : InverseBindingAdapter
    ){

    }
}