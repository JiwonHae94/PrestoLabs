package com.jiwon.prestolabs.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.lifecycleScope
import com.jiwon.prestolabs.viewmodel.InstrumentViewModel
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private val viewmodel : InstrumentViewModel by activityViewModels()

    init {
        lifecycleScope.launchWhenStarted {
            viewmodel.connectSocket()
        }
    }


}