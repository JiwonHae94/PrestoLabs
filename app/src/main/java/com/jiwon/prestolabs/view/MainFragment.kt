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
        binding.instruments = viewmodel.observableInstruments
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(view.context)
        binding.instrumentList.layoutManager = layoutManager
        binding.instrumentList.adapter = InstrumentAdapter().apply{ setHasStableIds(true) }
        binding.instrumentList.addItemDecoration(DividerItemDecoration(view.context, layoutManager.orientation))
        binding.instrumentList.setHasFixedSize(true)
    }
}