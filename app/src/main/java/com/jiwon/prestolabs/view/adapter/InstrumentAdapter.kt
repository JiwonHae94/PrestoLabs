package com.jiwon.prestolabs.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jiwon.prestolabs.R
import com.jiwon.prestolabs.databinding.InstrumentEntryBinding
import com.jiwon.prestolabs.model.Instrument
import com.jiwon.prestolabs.model.InstrumentState
import java.text.DecimalFormat

class InstrumentAdapter(
    private val instruments : List<Instrument>
) : RecyclerView.Adapter<InstrumentAdapter.ViewHolder>(){

    class ViewHolder(
        private val binding : InstrumentEntryBinding
    ) : RecyclerView.ViewHolder(binding.root){

        private val decimalFormat = DecimalFormat("#.##")

        fun bind(inst : Instrument){
            with(binding){
                changeTextview.text = decimalFormat.format(inst.lastChangePercentage)
                symbolTextview.text = inst.symbol
                priceTextview.text = inst.lastPrice.toString()
                volumeTextview.text = inst.volume24.toString()

                val color = if(inst.lastChangePercentage < 0) root.resources.getColor(R.color.red, null) else root.resources.getColor(R.color.green, null)
                changeTextview.setTextColor(color)
                priceTextview.setTextColor(color)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create new view
        val binding = InstrumentEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(instruments.get(position))
    }

    override fun getItemCount(): Int {
        return instruments.size
    }
}