package com.jiwon.prestolabs.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jiwon.prestolabs.R
import com.jiwon.prestolabs.databinding.MainFragmentBinding
import com.jiwon.prestolabs.model.Instrument.Companion.CompareByLastPrice
import com.jiwon.prestolabs.model.sortedMapBy
import com.jiwon.prestolabs.repository.InstrumentRepository
import com.jiwon.prestolabs.view.adapter.InstrumentAdapter
import com.jiwon.prestolabs.viewmodel.MainViewModel

class MainFragment : Fragment(R.layout.main_fragment) {
    private val viewmodel : MainViewModel by activityViewModels()
    lateinit var binding : MainFragmentBinding

    init {
        lifecycleScope.launchWhenStarted {
            viewmodel.connectSocket()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // binds xml to the MainFragmentBinding
        binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.instrumentList.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        binding.instrumentList.adapter = InstrumentAdapter(InstrumentRepository.getDummyData().sortedMapBy(::CompareByLastPrice))
        binding.instrumentList.addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
        binding.instrumentList.setHasFixedSize(true)
    }
}