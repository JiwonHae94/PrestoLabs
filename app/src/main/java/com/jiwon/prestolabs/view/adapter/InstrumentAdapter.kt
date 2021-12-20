package com.jiwon.prestolabs.view.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.jiwon.prestolabs.R
import com.jiwon.prestolabs.databinding.InstrumentItemViewBinding
import com.jiwon.prestolabs.databinding.InstrumentLoadingViewBinding
import com.jiwon.prestolabs.model.Instrument
import com.jiwon.prestolabs.model.InstrumentHashMap
import com.jiwon.prestolabs.model.sortedMapBy
import java.text.DecimalFormat

class InstrumentAdapter : RecyclerView.Adapter<InstrumentAdapter.ViewHolder>(){
    private var instruments : List<Instrument> = emptyList()
    abstract class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    class LoadingViewHolder(
        private val parent: ViewGroup,
        private val binding : InstrumentLoadingViewBinding = InstrumentLoadingViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
    ) : ViewHolder(binding.root){

        val imageLoader = ImageLoader.Builder(parent.context)
            .componentRegistry {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder(parent.context))
                } else {
                    add(GifDecoder())
                }
            }
            .build()

        init{
            // apply loading gif to the imageview
            binding.instrumentLoadingImage.load(R.drawable.loading_gif_icons8, imageLoader)
        }

    }

    class ItemViewHolder(
        private val parent: ViewGroup,
        private val binding: InstrumentItemViewBinding = InstrumentItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ) : ViewHolder(binding.root){
        fun bind(inst : Instrument){
            binding.instrument = inst
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == InstrumentLoading) {
            LoadingViewHolder(parent)
        } else {
            ItemViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder is ItemViewHolder && instruments.size > position){
            holder.bind(instruments.get(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(instruments.isEmpty()) InstrumentLoading else InstrumentLoaded
    }

    override fun getItemCount(): Int {
        return instruments.size
    }

    fun update(items : List<Instrument>){
        this.instruments = items
        notifyDataSetChanged()
    }

    companion object{
        private const val InstrumentLoading = 0
        private const val InstrumentLoaded = 1


        private val volumeFormat = DecimalFormat("#.00M")
        private val decimalFormat = DecimalFormat("#.##")

        private fun formatVolume(value : Long) : String{
            return if(value < 1000000) value.toString()
            else volumeFormat.format(value.toBigDecimal().movePointLeft(6))
        }

        @JvmStatic
        @BindingAdapter("bind:volume24")
        fun bindVolume24(view: TextView, var1 : Long){
            view.text = formatVolume(var1)
        }

        @JvmStatic
        @BindingAdapter("bind:changePercent")
        fun bindChangePercent(view: TextView, var1 : Double){
            view.text = decimalFormat.format(var1)
        }

        @JvmStatic
        @BindingAdapter("bind:instruments")
        fun bindRecyclerView(view : RecyclerView, items : InstrumentHashMap){
            val adapter = view.adapter as InstrumentAdapter
            Log.d("Test Binding Adapter", "items : ${items.keys}")
            adapter.update(items.values.toList())
        }
    }

}