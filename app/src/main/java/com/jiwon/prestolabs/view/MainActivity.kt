package com.jiwon.prestolabs.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jiwon.prestolabs.databinding.ActivityMainBinding
import com.jiwon.prestolabs.viewmodel.MainViewModel
import androidx.activity.viewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.jiwon.prestolabs.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // UI binding to reference binding
    private lateinit var binding : ActivityMainBinding

    private val viewmodel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewmodel = viewmodel
        setContentView(binding.root)

        //setSupportActionBar(binding.toolbar)


        if(savedInstanceState == null){
            supportFragmentManager.commit{
                setReorderingAllowed(true)
                add<MainFragment>(R.id.main_fragment_container)
            }
        }
    }
}