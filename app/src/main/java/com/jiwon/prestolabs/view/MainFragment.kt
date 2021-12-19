package com.jiwon.prestolabs.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.jiwon.prestolabs.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private val viewmodel : MainViewModel by activityViewModels()

    init {
        lifecycleScope.launchWhenStarted {
            viewmodel.connectSocket()
        }
    }


}