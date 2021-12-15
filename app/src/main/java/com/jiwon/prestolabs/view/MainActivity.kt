package com.jiwon.prestolabs.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jiwon.prestolabs.R
import com.jiwon.prestolabs.databinding.ActivityMainBinding
import com.jiwon.prestolabs.viewmodel.InstrumentViewModel
import androidx.activity.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), LifecycleOwner {

    // lifecycle registry to handle socket connection
    private lateinit var lifecycleRegistry: LifecycleRegistry

    // UI binding to reference binding
    private lateinit var binding : ActivityMainBinding

    private val viewmodel : InstrumentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.currentState = Lifecycle.State.CREATED

        binding.testBtn.setOnClickListener {
            viewmodel.connectSocket()
        }
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    override fun onDestroy() {
        super.onDestroy()


        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }
}