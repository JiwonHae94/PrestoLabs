package com.jiwon.prestolabs.view.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.jiwon.prestolabs.databinding.InstrumentItemViewBinding
import com.jiwon.prestolabs.databinding.InstrumentLoadingViewBinding
import com.jiwon.prestolabs.model.Instrument
import com.jiwon.prestolabs.model.InstrumentMap
import java.text.DecimalFormat

class InstrumentAdapter : RecyclerView.Adapter<InstrumentAdapter.ViewHolder>(){
    private var instruments : Array<Instrument> = emptyArray()
    abstract class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    // default comparator is SymbolComparator that orders instrument according to their symbol
    private var currentComparator = Instrument.SymbolComparator
    private var isReverse = false

    fun updateSorting(sorting : InstrumentMap.Sorting){
        // update current comparator
        currentComparator = when(sorting){
            InstrumentMap.Sorting.PriceAscending -> {
                isReverse = false
                Instrument.PriceComparator
            }
            InstrumentMap.Sorting.PriceDescending -> {
                isReverse = true
                Instrument.PriceComparator
            }
            InstrumentMap.Sorting.PercentChangeAscending -> {
                isReverse = false
                Instrument.PercentageChangeCompator
            }
            InstrumentMap.Sorting.PercentChangeDescending -> {
                isReverse = true
                Instrument.PercentageChangeCompator
            }
            InstrumentMap.Sorting.VolumeAscending -> {
                isReverse = false
                Instrument.Volume24Compator
            }
            InstrumentMap.Sorting.VolumeDescending -> {
                isReverse = true
                Instrument.Volume24Compator
            }

            InstrumentMap.Sorting.SymbolAscending -> {
                isReverse = false
                Instrument.SymbolComparator
            }
            InstrumentMap.Sorting.SymbolDescending -> {
                isReverse = true
                Instrument.SymbolComparator
            }
            else -> {
                // Default is None
                isReverse = false
                Instrument.SymbolComparator
            }
        }

        this.instruments = instruments.sortedWith(currentComparator).toTypedArray()
        if(isReverse)
            instruments.reverse()

        notifyDataSetChanged()
    }


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

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun update(items : Array<Instrument>){

        // set target instruments
        this.instruments = items.sortedWith(currentComparator).toTypedArray()
        if(isReverse){
            instruments.reverse()
        }

        // notify adapter of the update
        notifyDataSetChanged()

    }

    companion object{
        private const val InstrumentLoading = 0
        private const val InstrumentLoaded = 1

        private val volumeFormat = DecimalFormat("###,###.00M")
        private val decimalFormat = DecimalFormat("#.##%")
        private val thousandFormat = DecimalFormat("###,###.########")

        private fun formatVolume(value : Long) : String{
            return if(value < 1000000) thousandFormat.format(value)
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
            var sign = if(var1 > 0.0){
                "+"
            } else {
                ""
            }
            view.text = sign + decimalFormat.format(var1)
        }

        @JvmStatic
        @BindingAdapter("bind:price")
        fun bindPrice(view : TextView, var1 : Double){
            view.text = thousandFormat.format(var1)
        }


        @JvmStatic
        @BindingAdapter("bind:items")
        fun bindRecyclerView(view : RecyclerView, items : InstrumentMap){
            val adapter = view.adapter as InstrumentAdapter
            adapter.update(items.values.toTypedArray().clone())
        }
    }

}